package com.mundane.androidtechniqueapply.ui.activitity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;

import com.mundane.androidtechniqueapply.BuildConfig;
import com.mundane.androidtechniqueapply.R;
import com.mundane.androidtechniqueapply.base.BaseActionBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionActivity extends BaseActionBarActivity {

	@BindView(R.id.btn_request_permission)
	Button mBtnRequestPermission;
	@BindView(R.id.tv_permission_status)
	TextView mTvPermissionStatus;

	private final int CAMERA_REQUEST_CODE = 1;

	// TODO: 2017/3/16  事实上, 调用系统相机是不需要manifest里面的camera权限的, 现在看来只有二维码和自定义相机这种才需要camera权限
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.btn_request_permission)
	public void onClick() {
		requestPermission();
		//	申请相机权限, 这句代码会跳出那个系统定义的UI出来
		//	ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
	}

	@TargetApi(Build.VERSION_CODES.M)
	private void requestPermission() {
		int isHaveCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);//是否有相机权限
		if (isHaveCameraPermission != PackageManager.PERMISSION_GRANTED) {//	没有相机权限

			boolean showNotificationWhenRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
			if (showNotificationWhenRequest) {//如果用户点击了"禁止后不再询问", 或者第一次请求权限的时候, 这里会返回false
				new AlertDialog
						.Builder(this)
						.setMessage("申请相机权限")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//申请相机权限
								ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
							}
						})
						.show();
			} else {
				//申请相机权限, 这句代码会跳出那个系统定义的UI出来
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
			}
		} else {
			mTvPermissionStatus.setTextColor(Color.GREEN);
			mTvPermissionStatus.setText("相机权限已申请");
			// TODO: 2017/3/15  去拍照

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			int isHaveCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);//是否有相机权限
			if (isHaveCameraPermission == PackageManager.PERMISSION_GRANTED) {
				mTvPermissionStatus.setTextColor(Color.GREEN);
				mTvPermissionStatus.setText("相机权限已申请");
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				mTvPermissionStatus.setTextColor(Color.GREEN);
				mTvPermissionStatus.setText("相机权限已申请");
				// TODO: 2017/3/15  去拍照

			} else {
				//用户勾选了不再询问
				//提示用户手动打开权限
				if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
					new AlertDialog.Builder(this)
							.setMessage("相机权限已被禁止, 是否前往授权管理页面打开?")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									gotoMiuiPermission();
								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							})
							.show();
				}
			}
		}
	}

	/**
	 * 跳转到miui的权限管理页面
	 */
	private void gotoMiuiPermission() {
		Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
//		ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
		ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
		i.setComponent(componentName);
		i.putExtra("extra_pkgname", getPackageName());
		try {
//			startActivity(i);
			startActivityForResult(i, 1);
		} catch (Exception e) {
			e.printStackTrace();
			gotoMeizuPermission();
		}
	}

	/**
	 * 跳转到魅族的权限管理系统
	 */
	private void gotoMeizuPermission() {
		Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
		try {
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
//			gotoHuaweiPermission();
			startActivity(getAppDetailSettingIntent());
		}
	}

	/**
	 * 华为的权限管理页面
	 */
	private void gotoHuaweiPermission() {
		try {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
			intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
			intent.setComponent(comp);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			startActivity(getAppDetailSettingIntent());
		}

	}

	/**
	 * 获取应用详情页面intent
	 *
	 * @return
	 */
	private Intent getAppDetailSettingIntent() {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= 9) {
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			intent.setData(Uri.fromParts("package", getPackageName(), null));
		} else if (Build.VERSION.SDK_INT <= 8) {
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
		}
		return intent;
	}

}
