package com.example.selectallcb.views;

import com.example.selectallcb.R;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TopBar implements TopBarInterface{

private Activity mContext;
	
	private TopBarStyle mTopBarStyle;
	
	private LayoutInflater mLayoutInflater;
	
	private ViewGroup mContentContainer;
	
	/**普通模式的返回*/
	private ImageView mBackImage;
	
	/**普通模式的选择*/
	private TextView mSelectTextView;
	
	/**普通模式主标题*/
	private TextView mTitleView;
	
	/**普通模式副标题*/
	private TextView mSubTitleView;
	
	/**选择模式的取消*/
	private TextView mCancelView;
	
	/**选择模式的全选*/
	private TextView mChooseView;
	
	private String mTitle;
	
	private String mSubTitle;
	
	private int mTopBarHeight = 0;
	
	private OnClickListener mBackClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(!mContext.isFinishing())
				mContext.onBackPressed();
		}
	};
	
	private OnClickListener mSelectListener;
	
	private OnClickListener mCancelListener;
	
	private SelectChangeListener selectChangeListener;
	
	public TopBar(Activity context, ViewGroup group) {
		mContext = context;
		mContentContainer = group;
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public void setTopBarStyle(TopBarStyle style) {
		// TODO 自动生成的方法存根
		if (style == mTopBarStyle) {
			return;
		}
        mTopBarStyle = style;
        mContentContainer.removeAllViews();
        
        switch (style) {
		case NORMAL_STYLE:
			View normalView = mLayoutInflater.inflate(R.layout.top_bar_normal, mContentContainer);
			mBackImage = (ImageView) normalView.findViewById(R.id.back);
			mSelectTextView = (TextView) normalView.findViewById(R.id.select);
			mTitleView = (TextView) normalView.findViewById(R.id.title);
			mSubTitleView = (TextView) normalView.findViewById(R.id.subtitle);
			
			mBackImage.setOnClickListener(mBackClickListener);
			mSelectTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					if(mSelectListener != null) {
						mSelectListener.onClick(mSelectTextView);
					}
				}
			});
			break;
		case SELECT_STYLE:
			View batchView = mLayoutInflater.inflate(R.layout.top_bar_select, mContentContainer);
			mCancelView = (TextView) batchView.findViewById(R.id.cancel);
			mTitleView = (TextView) batchView.findViewById(R.id.title);
			mSubTitleView = (TextView) batchView.findViewById(R.id.subtitle);
			mChooseView = (TextView) batchView.findViewById(R.id.all);
			
			mCancelView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mCancelListener != null) {
						mCancelListener.onClick(mCancelView);
					}
				}
			});
			
			mChooseView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (selectChangeListener != null) {
						int checkedCount = selectChangeListener.getCheckedItemCount();
						int totalCount = selectChangeListener.getTotalItemCount();
						if (checkedCount != totalCount) {
							selectChangeListener.onSelectAllItems();
						} else {
							selectChangeListener.onClearAllItems();
						}
					}
				}
			});
			break;
		default:
			break;
		}
	}

	@Override
	public TopBarStyle getTopBarStyle() {
		// TODO 自动生成的方法存根
		return mTopBarStyle;
	}

	@Override
	public void setTopBarTtitle(String title) {
		// TODO 自动生成的方法存根
		mTitle = title;
		if (!TextUtils.isEmpty(title)) {
			mTitleView.setText(title);
		}
	}

	@Override
	public String getTopBarTtitle() {
		// TODO 自动生成的方法存根
		return mTitle;
	}

	@Override
	public void setTopBarSubTtitle(String subTitle) {
		// TODO 自动生成的方法存根
		mSubTitle = subTitle;
		if (!TextUtils.isEmpty(subTitle)) {
			mSubTitleView.setVisibility(View.VISIBLE);
			mSubTitleView.setText(subTitle);
		} else {
			mSubTitleView.setVisibility(View.GONE);
		}
	}

	@Override
	public String getTopBarSubTtitle() {
		// TODO 自动生成的方法存根
		return mSubTitle;
	}

	@Override
	public int getTopBarHeight() {
		// TODO 自动生成的方法存根
		if(mTopBarHeight == 0) {
			mTopBarHeight = mContext.getResources().getDimensionPixelSize(R.dimen.activity_topbar_height);
		}
		return mTopBarHeight;
	}

	@Override
	public void setBackOnClickListener(OnClickListener listener) {
		// TODO 自动生成的方法存根
		if (mBackImage != null) {
			if (listener == null) {
				mBackImage.setOnClickListener(mBackClickListener);
			} else {
				mBackImage.setOnClickListener(listener);
			}
		}
	}

	@Override
	public void setSelectOnClickListener(OnClickListener listener) {
		// TODO 自动生成的方法存根
		if(mTopBarStyle != TopBarStyle.NORMAL_STYLE) {
			Log.e("ERROR", "选择键错误");
		}
		mSelectListener = listener;
	}

	@Override
	public void setCancelOnClickListener(OnClickListener listener) {
		// TODO 自动生成的方法存根
		if(mTopBarStyle != TopBarStyle.SELECT_STYLE) {
			Log.e("ERROR", "取消键错误");
		}
		mCancelListener = listener;
	}

	@Override
	public void setOnSelectChangeListener(SelectChangeListener listener) {
		// TODO 自动生成的方法存根
		if(mTopBarStyle != TopBarStyle.SELECT_STYLE) {
			Log.e("ERROR", "全选错误");
		}
		selectChangeListener = listener;
		notifySelectedStateChanged();
	}

	@Override
	public void notifySelectedStateChanged() {
		// TODO 自动生成的方法存根
		if(mTopBarStyle != TopBarStyle.SELECT_STYLE) {
			Log.e("ERROR", "更新错误");
		}
		int checkedCount = 0;
        if (selectChangeListener != null) {
            checkedCount = selectChangeListener.getCheckedItemCount();
        }
        setTopBarTtitle(checkedCount + "项已选中");
        
        int totalCount = 0;
        if (selectChangeListener != null) {
            totalCount = selectChangeListener.getTotalItemCount();
        }
        setTopBarSubTtitle("总共" + totalCount + "项");
        
        if (totalCount != checkedCount) {
        	mChooseView.setText("全选");
        } else {
        	mChooseView.setText("全不选");
        }
	}

}
