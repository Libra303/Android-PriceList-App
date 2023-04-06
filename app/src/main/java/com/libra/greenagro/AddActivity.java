package com.libra.greenagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class AddActivity extends AppCompatActivity {

    private  Toolbar toolbar;
    private EditText name_et,size_et,price_et;
    private Button add_btn;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUIs();
        add_btn.setOnClickListener(add_btn_clicked);
        //가격 세자리 자동 콤마
        price_et.addTextChangedListener(getTextWatcher());
    }

    //UI 세팅
    private void initUIs() {
        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name_et = (EditText) findViewById(R.id.name_et);
        size_et = (EditText) findViewById(R.id.size_et);
        price_et = (EditText) findViewById(R.id.price_et);
        add_btn = (Button) findViewById(R.id.add_btn);
    }


    //액션바 뒤로가기 클릭시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //가격 세자리 자동 콤마
    public TextWatcher getTextWatcher(){
        DecimalFormat deformat = new DecimalFormat("###,###");
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                    result = deformat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                    price_et.setText(result);
                    price_et.setSelection(result.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        return watcher;
    }


    //버튼 클릭 리스너
    View.OnClickListener add_btn_clicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Product pd = new Product();
            pd.setName(name_et.getText().toString().trim());
            pd.setSize(size_et.getText().toString().trim());
            pd.setPrice(price_et.getText().toString().trim());

            Toast toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,-160);

            if (pd.getName().equals("") || pd.getPrice().equals("")){
                toast.setText("값을 입력해주세요");
                toast.show();
            }else{
                MainActivity.productDao.insertProdct(pd);
                toast.setText(pd.getName()+" 추가 성공!!");
                toast.show();
            }

            name_et.setText("");
            size_et.setText("");
            price_et.setText("");
        }
    };



}