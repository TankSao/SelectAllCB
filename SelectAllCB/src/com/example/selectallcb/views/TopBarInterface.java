package com.example.selectallcb.views;

import android.view.View.OnClickListener;

public interface TopBarInterface {
	public enum TopBarStyle{
		NORMAL_STYLE,//��ͨģʽ
		SELECT_STYLE,//ѡ��ģʽ
	};
	//��ʽ���
	public void setTopBarStyle(TopBarStyle style);
    
    public TopBarStyle getTopBarStyle();
    
    //�����븱����
	public void setTopBarTtitle(String title);
	
	public String getTopBarTtitle();
	
	public void setTopBarSubTtitle(String subTitle);
	
	public String getTopBarSubTtitle();
	
	public int getTopBarHeight();
	
	//��ͨģʽ���ؼ��ص�
	public void setBackOnClickListener(OnClickListener listener);
	
	//��ͨģʽѡ����ص�
	public void setSelectOnClickListener(OnClickListener listener);
	
	//ѡ��ģʽȡ�����ص�
	public void setCancelOnClickListener(OnClickListener listener);
	
	//ѡ��ص�
    public interface SelectChangeListener {
        /**
         * ȫѡ֮�ص�
         */
        public void onSelectAllItems();
        
        /**
         * ȫ��֮�ص�
         */
        public void onClearAllItems();
        
        /**
         * ��ȡ�б�����Ŀ
         */
        public int getTotalItemCount();
        
        /**
         * ��ȡ��ǰѡ����Ŀ
         */
        public int getCheckedItemCount();
    }
    
    public void setOnSelectChangeListener(SelectChangeListener listener);
    
    //����ѡ��״̬
    public void notifySelectedStateChanged();

}
