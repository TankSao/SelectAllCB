package com.example.selectallcb.views;

import android.view.View.OnClickListener;

public interface TopBarInterface {
	public enum TopBarStyle{
		NORMAL_STYLE,//普通模式
		SELECT_STYLE,//选择模式
	};
	//样式风格
	public void setTopBarStyle(TopBarStyle style);
    
    public TopBarStyle getTopBarStyle();
    
    //标题与副标题
	public void setTopBarTtitle(String title);
	
	public String getTopBarTtitle();
	
	public void setTopBarSubTtitle(String subTitle);
	
	public String getTopBarSubTtitle();
	
	public int getTopBarHeight();
	
	//普通模式返回键回调
	public void setBackOnClickListener(OnClickListener listener);
	
	//普通模式选择键回调
	public void setSelectOnClickListener(OnClickListener listener);
	
	//选择模式取消键回调
	public void setCancelOnClickListener(OnClickListener listener);
	
	//选择回调
    public interface SelectChangeListener {
        /**
         * 全选之回调
         */
        public void onSelectAllItems();
        
        /**
         * 全清之回调
         */
        public void onClearAllItems();
        
        /**
         * 获取列表项数目
         */
        public int getTotalItemCount();
        
        /**
         * 获取当前选中数目
         */
        public int getCheckedItemCount();
    }
    
    public void setOnSelectChangeListener(SelectChangeListener listener);
    
    //更新选择状态
    public void notifySelectedStateChanged();

}
