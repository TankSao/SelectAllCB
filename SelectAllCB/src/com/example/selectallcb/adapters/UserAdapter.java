package com.example.selectallcb.adapters;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selectallcb.R;
import com.example.selectallcb.beans.User;

@SuppressLint("NewApi") public class UserAdapter extends BaseAdapter implements OnItemClickListener, OnItemLongClickListener{

	private List<User> mListData;
	private Context context;
	private SparseArray<Boolean> mSelectState;
	private boolean isSelect;
	public void setmListData(List<User> mListData) {
		this.mListData = mListData;
	}
	public void setmSelectState(SparseArray<Boolean> mSelectState) {
		this.mSelectState = mSelectState;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public UserAdapter() {
		// TODO 自动生成的构造函数存根
	}
	public UserAdapter(Context context,List mList,SparseArray<Boolean> mSelectState,boolean isSelect) {
		// TODO 自动生成的构造函数存根
		this.mListData = mList;
		this.context = context;
		this.isSelect = isSelect;
		this.mSelectState = mSelectState;
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mListData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return mListData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder holder = null;
		View view = convertView;
		if(view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.list_user_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		User data = mListData.get(position);
		holder.image.setImageResource(data.getImgId());
		holder.name.setText(data.getName());
		holder.tel.setText(data.getTel());
		
		int _id = data.getId();
        if(isSelect) {
            boolean selected = mSelectState.get(_id, false);
            holder.checkBox.setChecked(selected);
        }
		switchBatchModel(holder);
		return view;
	}
	
	private void switchBatchModel(ViewHolder holder) {
        if (isSelect) {
            CheckBoxAnimatorHelper.animateShowing(holder, this, true);
        } else {
            CheckBoxAnimatorHelper.animateHiding(holder, this, true);
        }
    }
	
	
	
	class ViewHolder {
		CheckBox checkBox;
		View contentLayout;
		ImageView image;
		TextView name;
		TextView tel;

		public ViewHolder(View view) {
			checkBox = (CheckBox) view.findViewById(R.id.check_box);
			contentLayout = (View) view.findViewById(R.id.content_Layout);
			image = (ImageView) view.findViewById(R.id.image);
			name = (TextView) view.findViewById(R.id.name);
			tel = (TextView) view.findViewById(R.id.tel);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		
	}
	
	public static final class CheckBoxAnimatorHelper {

		public static final int DURATION = 400;

		public static void animateShowing(final ViewHolder holder,
				final UserAdapter adapter, boolean isAnimate) {
			final CheckBox checkBox = holder.checkBox;
			if (checkBox.getVisibility() == View.VISIBLE) {
				return;
			}
			checkBox.setVisibility(View.VISIBLE);
			checkBox.setAlpha(0.0f);
			final int widthSpec = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			final int heightSpec = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			checkBox.measure(widthSpec, heightSpec);
			ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) checkBox
					.getLayoutParams();
			final long transValue = checkBox.getMeasuredWidth() + lp.leftMargin
					+ lp.rightMargin;

			if (!isAnimate) {
				checkBox.setAlpha(1.0f);
				holder.contentLayout.setTranslationX(transValue);
				return;
			}

			final ObjectAnimator transBodyAnimator = new ObjectAnimator();
			final PropertyValuesHolder trans = PropertyValuesHolder.ofFloat(
					"TranslationX", 0.0f, transValue);
			transBodyAnimator.setTarget(holder.contentLayout);
			transBodyAnimator.setValues(trans);
			transBodyAnimator.setDuration(DURATION);

			ObjectAnimator checkBoxAnim = new ObjectAnimator();
			final PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(
					"ScaleX", 0.0f, 1.0f);
			final PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(
					"ScaleY", 0.0f, 1.0f);
			final PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(
					"Alpha", 0.0f, 1.0f);
			checkBoxAnim.setValues(scaleX, scaleY, alpha);
			checkBoxAnim.setTarget(holder.checkBox);
			checkBoxAnim.setDuration(DURATION);
			checkBoxAnim.setInterpolator(new DecelerateInterpolator());
			checkBoxAnim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					checkBox.setTag("animating");
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					// adapter.setCheckBoxAnimator(false);
					checkBox.setTag("animated");
				}
			});
			if (!(checkBox.getTag() != null && "animating".equals(checkBox
					.getTag()))) {
				// 若正在播放动画，则不继续播放动画
				transBodyAnimator.start();
				checkBoxAnim.start();
			}
		}

		public static void animateHiding(final ViewHolder holder,
				final UserAdapter adapter, boolean isAnimate) {
			final CheckBox checkBox = holder.checkBox;
			final View tansBody = holder.contentLayout;

			if (checkBox.getVisibility() == View.GONE) {
				return;
			}
			ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) checkBox
					.getLayoutParams();
			final float transValue = checkBox.getMeasuredWidth()
					+ lp.leftMargin + lp.rightMargin;

			if (!isAnimate) {
				checkBox.setVisibility(View.GONE);
				holder.contentLayout.setTranslationX(0.0f);
				return;
			}
			final ObjectAnimator transBodyAnimator = new ObjectAnimator();
			final PropertyValuesHolder trans = PropertyValuesHolder.ofFloat(
					"TranslationX", transValue, 0.0f);
			transBodyAnimator.setTarget(tansBody);
			transBodyAnimator.setValues(trans);
			transBodyAnimator.setDuration(DURATION);

			ObjectAnimator checkBoxAnim = new ObjectAnimator();
			final PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(
					"ScaleX", 1.0f, 0.0f);
			final PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(
					"ScaleY", 1.0f, 0.0f);
			final PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(
					"Alpha", 1.0f, 0.0f);
			checkBoxAnim.setValues(scaleX, scaleY, alpha);
			checkBoxAnim.setTarget(checkBox);
			checkBoxAnim.setDuration(DURATION);
			checkBoxAnim.setInterpolator(new AccelerateInterpolator());
			checkBoxAnim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationStart(Animator animation) {
					checkBox.setTag("animating");
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					// adapter.setCheckBoxAnimator(false);
					checkBox.setScaleX(1.0f);
					checkBox.setScaleY(1.0f);
					checkBox.setAlpha(1.0f);
					checkBox.setVisibility(View.GONE);
					checkBox.setTag("animated");
				}
			});
			if (!(checkBox.getTag() != null && "animating".equals(checkBox
					.getTag()))) {
				transBodyAnimator.start();
				checkBoxAnim.start();
			}
		}
	}

}

