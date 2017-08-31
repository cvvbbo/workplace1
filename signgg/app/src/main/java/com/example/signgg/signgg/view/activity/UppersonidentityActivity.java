package com.example.signgg.signgg.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.R;
import com.example.signgg.signgg.net.bean.Identitybean;
import com.example.signgg.signgg.net.httphelper;
import com.example.signgg.signgg.utils.Constant;
import com.example.signgg.signgg.utils.GsonUtil;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.ToastUtil;
import com.example.signgg.signgg.utils.sharepreferenceUtils;
import com.example.signgg.signgg.view.ui.Mydialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiong on 2017/8/15.
 */

public class UppersonidentityActivity extends AppCompatActivity {


    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.upbt)
    Button upbt;
    @BindView(R.id.front_id)
    ImageView frontId;
    @BindView(R.id.reverse_id)
    ImageView reverseId;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.identitynum)
    EditText identitynum;

    //从相册中获得的bitmap对象
    private Bitmap albumbitmap;

    //照片的名字
    private String photoname1;

    //从手机拍照中获得的对象
    // private Bitmap makephotobitmap;

    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ProgressDialog pg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_person_identity);
        ButterKnife.bind(this);

        makepermission();

        inintToolbar();

        upbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pg = new ProgressDialog(UppersonidentityActivity.this);
                pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pg.setTitle("验证中，请耐心等待");
                pg.setCancelable(false);
                pg.show();




                //暂时注释
//                Intent intent = new Intent(UppersonidentityActivity.this, Loadcheckidentity.class);
//                startActivity(intent);
//                //这个就不用结束界面了
//
//                //提交之后有个待审核界面，审核之后才是正常的界面。审核失败又是重新提交的界面
//                //审核的json就有三种，未审核（checkidentityActivity），待审核，已通过（beginActivity）
//
//                //他们的审核是实时的，上传了照片就能判断，没这么麻烦
//                sharepreferenceUtils.saveBooleandata(UppersonidentityActivity.this, "isidentity", false);
//                finish();

                File appDir = new File(Environment.getExternalStorageDirectory(), "emiaoqian");
                File file1 = new File(appDir, "stream_idcard_front.jpg");
                File file2 = new File(appDir, "stream_idcard_back.jpg");
                final String[] filepath=new String[]{file1.getPath(),file2.getPath()};


                //这个也是个耗时操作，需要在子线程。。。(因为太慢了)

                //okhttp本身就是在子线程的。
                        httphelper.create().upImage(filepath, Constant.UP_IMAGE_URL, new httphelper.httpcallback() {
                            @Override
                            public void success(String s) {
                                LogUtil.e("success"+s);

                                Identitybean identitybean = GsonUtil.parseJsonToBean(s, Identitybean.class);
                                String code = identitybean.getCode();
                                int errorCode = identitybean.getErrorCode();
                                LogUtil.e(code+" ");
                                if ("100000".equals(code) ) {
                                    Identitybean.DataBean data = identitybean.getData();
                                    String msg = identitybean.getMsg();
                                    ToastUtil.showToast(msg);
                                    String name = data.getName();
                                    sharepreferenceUtils.saveStringdata(MyApplication.mcontext, "user_name", name);
                                    //转跳
                                    Intent intent=new Intent();
                                    String user_name = sharepreferenceUtils.getStringdata(MyApplication.mcontext, "user_name", "");
                                    intent.putExtra("user_name",user_name);
                                    setResult(2,intent);
                                    //转跳
                                    pg.dismiss();
                                    finish();
                                }else if (errorCode==200011){
                                    String error=identitybean.getMsg();
                                    ToastUtil.showToast(error);
                                    pg.dismiss();
                                }else  if (errorCode==200012){
                                    String error=identitybean.getMsg();
                                    ToastUtil.showToast(error);
                                    pg.dismiss();
                                    //服务器的字段，int和string的是不同的
                                }else if (code==String.valueOf(400)){
                                    ToastUtil.showToast("服务器出错");
                                }
                            }

                            @Override
                            public void fail(Exception e) {
                                LogUtil.e(e+"--fail");
                                ToastUtil.showToast("抱歉服务器出错了~");
                                pg.dismiss();
                            }
                        });


//                Intent intent=new Intent();
//                String user_name = sharepreferenceUtils.getStringdata(MyApplication.mcontext, "user_name", "");
//                intent.putExtra("user_name",user_name);
//                setResult(2,intent);
               // finish();



            }
        });


        //正面上传
        frontId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //还要添加个dialog。。
                photoname1 = "stream_idcard_front.jpg";
                LogUtil.e("被点击了");
                Mydialog mydialog = new Mydialog(UppersonidentityActivity.this);
                zheng = true;
                mydialog.show();



            }

        });



        //背面上传
        reverseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoname1 = "stream_idcard_back.jpg";
                LogUtil.e("被点击了");
                Mydialog mydialog = new Mydialog(UppersonidentityActivity.this);
                zheng = false;
                mydialog.show();




            }
        });
    }


    private void inintToolbar() {
        //这个是登录的
        setSupportActionBar(tb);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("实名认证");

        //这个通用（toolbar），自定义的还是非自定义的都是通用的。
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //返回上一个身份验证界面
                finish();
            }
        });
    }

//    private void doTakePhoto() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用系统相机
//        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, 100);  //用户点击了从相机获取
//    }


    //保存图片，这个是保存bitmap的图片。
    public static void saveImage(Context m, Bitmap bmp, String photoname) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "emiaoqian");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = photoname;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //100%品质就是不会压缩，这里只是把图片保存到另一个地方
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.e("---", "执行了么");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        m.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
    }


    public void deleimage(Context m) {

        File appDir = new File(Environment.getExternalStorageDirectory(), "emiaoqian");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "haha.jpg";
        File file = new File(appDir, fileName);
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = m.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + file + "'";
        //删除图片
        mContentResolver.delete(uri, where, null);

        m.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));


    }

    //6.0的动态权限
    public void makepermission() {
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(UppersonidentityActivity.this, Manifest.permission.CAMERA);
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UppersonidentityActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
        }
    }


    //压缩图片的方法
    public Bitmap compressImage(String filepath) {
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        Point p = new Point();

        getWindowManager().getDefaultDisplay().getSize(p);

        width = p.x;
        height = p.y;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //下面这个是获取不到大小的，因为加载进内存的大小为0
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        BitmapFactory.decodeFile(filepath, options);
        //Log.e("--压缩之前", bitmap.getByteCount() + " ");
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int index = 1;
        if (outHeight > height || outWidth > width) {
            float heightRate = outHeight / height;
            float widthrate = outHeight / width;

            index = (int) Math.max(heightRate, widthrate);
        }
        options.inSampleSize = index;
        options.inJustDecodeBounds = false;
        Bitmap afterbitmap = BitmapFactory.decodeFile(filepath, options);
        //Log.e("--压缩之后", afterbitmap.getByteCount() + " ");
        return afterbitmap;
    }





    //保存图片，这个是保存bitmap的图片从相册
    public static File saveImageofalbum(Context m, Bitmap bmp, String photoname) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "emiaoqian");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = photoname;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //100%品质就是不会压缩
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.e("-从相册中选取，并保存-", "执行了么");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //因为这个方法是为了临时保存的，所以看看不刷新会怎么样
        m.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));

        return file;

    }

    private static boolean zheng = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:

                    Bitmap makephotobitmap = compressImage(new File(Environment.getExternalStorageDirectory(), "image.jpg").getPath());
                    LogUtil.e("---photoname有值么" + photoname1);
                    saveImage(UppersonidentityActivity.this, makephotobitmap, photoname1);

                    //设置图片这里肯定不能写死，得写个判断的！！而且这里很精髓
                    if (zheng) {
                        frontId.setImageBitmap(makephotobitmap);

                    } else {
                        reverseId.setImageBitmap(makephotobitmap);
                    }





                    break;

                //从相册中获取
                case 102:
                    ContentResolver resolver = getContentResolver();
                    //照片的原始资源地址，这样获取并不是压缩过的，如果拍照的图片也是这样获取就是压缩过的！！
                    Uri originalUri = data.getData();

                    //使用ContentProvider通过URI获取原始图片
                    try {
                        //下面这个是原始图片的bitmap，但是并不在本应用的文件夹下面
                        //这里就是成员变量
                        albumbitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                        //先放图片，然后在保存，很骚。。。，这样展示就快了

                        //这里不能相册就放在背面
                        if (zheng) {
                            frontId.setImageBitmap(albumbitmap);
                        } else {
                            reverseId.setImageBitmap(albumbitmap);
                        }

                        //这个保存有点耗时，差不多10秒，才完成，先放在子线程中，现在主线程中
                        new Thread() {
                            @Override
                            public void run() {
                                // 第一次保存时将系统的保存在自己的文件夹中

                                LogUtil.e("---photoname有值么" + photoname1);
                                File file = saveImageofalbum(UppersonidentityActivity.this, albumbitmap, photoname1);
                                //最后压缩保存
                                Bitmap afterbitmap = compressImage(file.getPath());

                                //这个是最终保存的的
                                saveImage(UppersonidentityActivity.this, afterbitmap, photoname1);

                            }
                        }.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }
}
