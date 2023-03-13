package com.libra.greenagro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Product> list;

    public CustomAdapter(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
        int i = position;
        holder.pd_name.setText(list.get(position).getName());
        holder.pd_size.setText(list.get(position).getSize());
        holder.pd_price.setText(list.get(position).getPrice());

        holder.itemView.setTag(position);
        
        //요 부분 코드 정리 예정
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(holder.pd_name.getText()+" / "+holder.pd_size.getText()+" / "+holder.pd_price.getText());
                builder.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int list_index) {

                        switch (list_index){

                            case 0:
                                //수정 눌렀을때
                                AlertDialog.Builder upbuilder = new AlertDialog.Builder(view.getContext());
                                LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
                                View dialogView = layoutInflater.inflate(R.layout.custom_dialog,null);

                                EditText name_cd = (EditText) dialogView.findViewById(R.id.name_cd);
                                EditText size_cd =(EditText) dialogView.findViewById(R.id.size_cd);
                                EditText price_cd =(EditText) dialogView.findViewById(R.id.price_cd);

                                name_cd.setText(holder.pd_name.getText().toString());
                                size_cd.setText(holder.pd_size.getText().toString());
                                price_cd.setText(holder.pd_price.getText().toString());

                                upbuilder.setView(dialogView);

                                upbuilder.setPositiveButton("수정하기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int index) {
                                        Product pd = list.get(i);
                                        pd.setName(name_cd.getText().toString().trim());
                                        pd.setSize(size_cd.getText().toString().trim());
                                        pd.setPrice(price_cd.getText().toString().trim());

                                        MainActivity.productDao.updateProduct(pd);
                                        notifyDataSetChanged();
                                        Toast.makeText(view.getContext(), holder.pd_name.getText()+" 수정완료", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                upbuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {}
                                });
                                upbuilder.create().show();
                                break;

                            case 1 :
                                //삭제 눌렀을때

                                AlertDialog.Builder delbuilder = new AlertDialog.Builder(view.getContext());
                                delbuilder.setTitle("정말 삭제하시겠습니까?");
                                delbuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int index) {
                                        MainActivity.productDao.deleteById(list.get(i).getId());
                                        list.remove(i);
                                        notifyDataSetChanged();
                                        Toast.makeText(view.getContext(), holder.pd_name.getText()+" 삭제완료", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                delbuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int index) {

                                    }
                                });
                                delbuilder.create().show();
                        }

                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView pd_name;
        protected TextView pd_size;
        protected TextView pd_price;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pd_name = (TextView) itemView.findViewById(R.id.name_list);
            this.pd_size = (TextView) itemView.findViewById(R.id.size_list);
            this.pd_price = (TextView) itemView.findViewById(R.id.price_list);
        }
    }

    //리스트 세팅
    public void setList(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
