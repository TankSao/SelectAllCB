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
	
	/**��ͨģʽ�ķ���*/
	private ImageView mBackImage;
	
	/**��ͨģʽ��ѡ��*/
	private TextView mSelectTextView;
	
	/**��ͨģʽ������*/
	private TextView mTitleView;
	
	/**��ͨģʽ������*/
	private TextView mSubTitleView;
	
	/**ѡ��ģʽ��ȡ��*/
	private TextView mCancelView;
	
	/**ѡ��ģʽ��ȫѡ*/
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
		// TODO �Զ����ɵķ������
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
					// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		return mTopBarStyle;
	}

	@Override
	public void setTopBarTtitle(String title) {
		// TODO �Զ����ɵķ������
		mTitle = title;
		if (!TextUtils.isEmpty(title)) {
			mTitleView.setText(title);
		}
	}

	@Override
	public String getTopBarTtitle() {
		// TODO �Զ����ɵķ������
		return mTitle;
	}

	@Override
	public void setTopBarSubTtitle(String subTitle) {
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		return mSubTitle;
	}

	@Override
	public int getTopBarHeight() {
		// TODO �Զ����ɵķ������
		if(mTopBarHeight == 0) {
			mTopBarHeight = mContext.getResources().getDimensionPixelSize(R.dimen.activity_topbar_height);
		}
		return mTopBarHeight;
	}

	@Override
	public void setBackOnClickListener(OnClickListener listener) {
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		if(mTopBarStyle != TopBarStyle.NORMAL_STYLE) {
			Log.e("ERROR", "ѡ�������");
		}
		mSelectListener = listener;
	}

	@Override
	public void setCancelOnClickListener(OnClickListener listener) {
		// TODO �Զ����ɵķ������
		if(mTopBarStyle != TopBarStyle.SELECT_STYLE) {
			Log.e("ERROR", "ȡ��������");
		}
		mCancelListener = listener;
	}

	@Override
	public void setOnSelectChangeListener(SelectChangeListener listener) {
		// TODO �Զ����ɵķ������
		if(mTopBarStyle != TopBarStyle.SELECT_STYLE) {
			Log.e("ERROR", "ȫѡ����");
		}
		selectChangeListener = listener;
		notifySelectedStateChanged();
	}

	@Override
	public void notifySelectedStateChanged() {
		// TODO �Զ����ɵķ������
		if(mTopBarStyle != TopBarStyle.SELECT_STYLE) {
			Log.e("ERROR", "���´���");
		}
		int checkedCount = 0;
        if (selectChangeListener != null) {
            checkedCount = selectChangeListener.getCheckedItemCount();
        }
        setTopBarTtitle(checkedCount + "����ѡ��");
        
        int totalCount = 0;
        if (selectChangeListener != null) {
            totalCount = selectChangeListener.getTotalItemCount();
        }
        setTopBarSubTtitle("�ܹ�" + totalCount + "��");
        
        if (totalCount != checkedCount) {
        	mChooseView.setText("ȫѡ");
        } else {
        	mChooseView.setText("ȫ��ѡ");
        }
	}

}
