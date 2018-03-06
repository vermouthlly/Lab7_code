package com.example.dell.lab5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.Map;
/**
 * Created by dell on 2017/10/26.
 */

public abstract class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder>{
    private Context mContext;
    private  int mLayoutId;
    private  List<Map<String,Object>> mDatas;
    private OnItemClickListener mOnItemClickListener;

    static public class ViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mConvertView;

        public ViewHolder(Context context,View itemView,ViewGroup parent) {
            super(itemView);
            mConvertView=itemView;
            mViews=new SparseArray<View>();
        }
        public static ViewHolder get(Context context,ViewGroup parent,int layoutId){
            View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            ViewHolder holder = new ViewHolder(context,itemView,parent);
            return holder;
        }

        public <T extends  View> T getView(int viewId){
            View view = mViews.get(viewId);
            if (view==null){
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return  (T) view;
        }
    }

    public CommonAdapter(Context context, int layoutId, List datas){
        mContext = context;
        mLayoutId=layoutId;
        mDatas=datas;
    }
    protected abstract void convert(ViewHolder holder,Map<String,Object> s);
    @Override
    public  ViewHolder onCreateViewHolder(final ViewGroup parent,int viewType){
        ViewHolder viewHolder = (ViewHolder) ViewHolder.get(mContext,parent,mLayoutId);
        return  viewHolder;
    }

    @Override
    public void  onBindViewHolder(final ViewHolder holder,int position){
        convert(holder,mDatas.get(position));
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v){
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }
    @Override
    public int getItemCount(){return mDatas.size();}

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(CommonAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

}
