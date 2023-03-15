package com.libra.greenagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private  Toolbar toolbar;
    private EditText name_et,size_et,price_et;
    private Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUIs();
        add_btn.setOnClickListener(add_btn_clicked);
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