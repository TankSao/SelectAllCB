package com.example.selectallcb;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selectallcb.adapters.UserAdapter;
import com.example.selectallcb.beans.User;
import com.example.selectallcb.views.TopBar;
import com.example.selectallcb.views.TopBarInterface.SelectChangeListener;


public class MainActivity extends ActionBarActivity {
	
	private LinearLayout mTopLayout;//顶部布局
	private ListView mListView;//列表
	private TopBar topbar;//标题栏
	private UserAdapter userAdapter;//适配器
	private List<User> mListData = new ArrayList<User>();//数据
	private boolean isSelect;//是否进入选择模式
    private Animation mAnimationShow;//标题栏显示动画
	private Animation mAnimationHide;//标题栏隐藏动画
	private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();//记录选择状态
	private TextView sure;//确定
	private RelativeLayout relativeSure;
	/**头像Id*/
	private int[] imgIds = new int[] {
			R.drawable.personal_center_avatar_female1,
			R.drawable.personal_center_avatar_female2,
			R.drawable.personal_center_avatar_male1,
			R.drawable.personal_center_avatar_male2,
			R.drawable.personal_center_avatar_female1,
			R.drawable.personal_center_avatar_female2,
			R.drawable.personal_center_avatar_male1,
			R.drawable.personal_center_avatar_male2,
			R.drawable.personal_center_avatar_female1,
			R.drawable.personal_center_avatar_female2,
			R.drawable.personal_center_avatar_male1,
			R.drawable.personal_center_avatar_male2,
			R.drawable.personal_center_avatar_female1,
			R.drawable.personal_center_avatar_female2,
			R.drawable.personal_center_avatar_male1,
			R.drawable.personal_center_avatar_male2,
			R.drawable.personal_center_avatar_female1,
			R.drawable.personal_center_avatar_female2,
			R.drawable.personal_center_avatar_male1,
			R.drawable.personal_center_avatar_male2};
	/**名字*/
	private String[] names  = new String[]{
			"追风少年","谭桂涛","镜中人","罗宾","谭克骚",
			"追风少年","谭桂涛","镜中人","罗宾","谭克骚",
			"追风少年","谭桂涛","镜中人","罗宾","谭克骚",
			"追风少年","谭桂涛","镜中人","罗宾","谭克骚",
	};
	/**电话*/
	private String[] tels  = new String[]{
			"13465678141","13465678142","13465678143","13465678144","13465678145",
			"13465678146","13465678147","13465678148","13465678149","13465678140",
			"13465678151","13465678152","13465678153","13465678154","13465678155",
			"13465678156","13465678157","13465678158","13465678159","13465678150",
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initAnimation();//初始化动画
        initView();
        initTopBar();
        initData();
    }
    private void initData() {
		// TODO 自动生成的方法存根
		for(int i=0;i<imgIds.length;i++){
			User user = new User();
			user.setId(i);
			user.setImgId(imgIds[i]);
			user.setName(names[i]);
			user.setTel(tels[i]);
			mListData.add(user);
		}
		updateListView();
	}
    private void updateListView() {
		if(userAdapter == null) {
			userAdapter = new UserAdapter(this,mListData,mSelectState,isSelect);
			mListView.setAdapter(userAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO 自动生成的方法存根
					User bean = mListData.get(arg2);
			        if(isSelect) {
			            //ViewHolder holder = (ViewHolder) arg1.getTag();
			            int _id = (int) bean.getId();
			            boolean selected = !mSelectState.get(_id, false);
			            //holder.checkBox.toggle();
			            if(selected) {
			            	mSelectState.put(_id, true);
			            } else {
			            	mSelectState.delete(_id);
			            }
			            
			            topbar.notifySelectedStateChanged();
			            userAdapter.setmListData(mListData);
						userAdapter.setmSelectState(mSelectState);
						userAdapter.setSelect(isSelect);
						userAdapter.notifyDataSetChanged();
			        }
				}
			});
			mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO 自动生成的方法存根
					isSelect = true;
					relativeSure.setVisibility(View.VISIBLE);
					sure.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO 自动生成的方法存根
							List selected = getSelectedIds();
							String ids = "";
							for(int i = 0;i<selected.size();i++){
								ids+=selected.get(i)+",";
							}
							Toast.makeText(MainActivity.this, "选择项为："+ids, Toast.LENGTH_LONG).show();
						}
					});
					int _id = (int) mListData.get(arg2).getId();
		            mSelectState.put(_id, true);
		            rmTopBarView();
		            updateListView();
					return true;
				}
			});
		} else {
			//mListData,mSelectState,isSelect
			userAdapter.setmListData(mListData);
			userAdapter.setmSelectState(mSelectState);
			userAdapter.setSelect(isSelect);
			userAdapter.notifyDataSetChanged();
		}
	}
    
    private final List<Integer> getSelectedIds() {
        ArrayList<Integer> selectedIds = new ArrayList<Integer>();
        for (int index = 0; index < mSelectState.size(); index++) {
            if (mSelectState.valueAt(index)) {
                selectedIds.add(mSelectState.keyAt(index));
            }
        }
        return selectedIds;
    }
    
    private void rmTopBarView() {
		mTopLayout.startAnimation(mAnimationHide);
		mAnimationHide.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				addTopBarView();
			}
		});
		mTopLayout.setVisibility(View.GONE);
	}
	
	private void addTopBarView() {
		initTopBar();
    	mTopLayout.setVisibility(View.VISIBLE);
    	mTopLayout.startAnimation(mAnimationShow);
	}
	private void initTopBar() {
		// TODO 自动生成的方法存根
		topbar = new TopBar(this, mTopLayout);
		updateTopBar();
	}
	private void updateTopBar() {
		if(isSelect) {
			topbar.setTopBarStyle(com.example.selectallcb.views.TopBarInterface.TopBarStyle.SELECT_STYLE);
			topbar.setCancelOnClickListener(new onCancelClickListener());
			topbar.setOnSelectChangeListener(selectChangeListener);
			relativeSure.setVisibility(View.VISIBLE);
		} else {
			topbar.setTopBarStyle(com.example.selectallcb.views.TopBarInterface.TopBarStyle.NORMAL_STYLE);
			topbar.setTopBarTtitle("普通模式");
			relativeSure.setVisibility(View.GONE);
			topbar.setSelectOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					isSelect = true;
					relativeSure.setVisibility(View.VISIBLE);
					sure.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO 自动生成的方法存根
							List selected = getSelectedIds();
							String ids = "";
							for(int i = 0;i<selected.size();i++){
								ids+=selected.get(i)+",";
							}
							Toast.makeText(MainActivity.this, "选择项为："+ids, Toast.LENGTH_LONG).show();
						}
					});
		            rmTopBarView();
		            updateListView();
				}
			});
		}
	}
	private SelectChangeListener selectChangeListener = new SelectChangeListener() {

		@Override
		public void onSelectAllItems() {
            if (mListData != null) {
                mSelectState.clear();
                int size = mListData.size();
                if (size == 0) {
                    return;
                }
                for (int i = 0; i < size; i++) {
                    int _id = (int) mListData.get(i).getId();
                    mSelectState.put(_id, true);
                }
                updateListView();
                topbar.notifySelectedStateChanged();
            }
		}

		@Override
		public void onClearAllItems() {
            if (userAdapter != null) {
                mSelectState.clear();
                updateListView();
                topbar.notifySelectedStateChanged();
            }
		}

		@Override
		public int getTotalItemCount() {
			if(mListData != null) {
				return mListData.size();
			}
			return 0;
		}

		@Override
		public int getCheckedItemCount() {
			return mSelectState.size();
		}
		
	};
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode) {
            if(isSelect) {
                cancelBatchModel();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
	private class onCancelClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			cancelBatchModel();
		}
		
	}
	private void cancelBatchModel() {
        isSelect = false;
        mSelectState.clear();
        relativeSure.setVisibility(View.GONE);
        updateListView();
        rmTopBarView();
    }
	private void initView() {
		// TODO 自动生成的方法存根
    	mTopLayout = (LinearLayout) findViewById(R.id.top_bar);
		mListView = (ListView) findViewById(R.id.listview);
		relativeSure = (RelativeLayout) findViewById(R.id.relative_sure);
		sure = (TextView) findViewById(R.id.sure);
	}
	private void initAnimation() {
        mAnimationHide = new AlphaAnimation(1.0f, 0.0f);
        mAnimationHide.setInterpolator(new DecelerateInterpolator());
        mAnimationHide.setDuration(200);
        mAnimationShow = new AlphaAnimation(0.0f, 1.0f);
        mAnimationShow.setInterpolator(new AccelerateInterpolator());
        mAnimationShow.setDuration(600);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
