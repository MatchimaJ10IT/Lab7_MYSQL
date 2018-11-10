package com.example.admin.lab7;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText txtStdid;
    private EditText txtStdName;
    private EditText txtStdTel;
    private EditText txtStdEmail;
    private EditText editStdid = null;
    private EditText editStdName = null;
    private EditText editStdTel = null;
    private EditText editStdEmail = null;
    private Button btnSave;
    private Button btnShow;
    private ListView dataView;
    private ListView clickView;
    private MySQLConnect mySQLConnect;
    private List<String> items;
    private ArrayAdapter<String> adt;
    private String dataStdId = null ;
    private String dataStdName = null ;
    private String dataStdTel = null ;
    private String dataStdEmail = null ;

    private View promptView = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        update();

        clickView = findViewById(R.id.dataView);

        clickView.setOnItemClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(Adapter<?> adapterView, View view, int pos, Lond l) {

                String selectedFromList = (clickView.getItemAtPositin(pos).toString() + " ");
                int indextel;
                String caltel = "";
                dataStdId = selectedFromList.substring(0, 11);
                caltel = selectedFromList.substring(12);
                indextel = caltel.indexOf("0");
                dataStdName = caltel.substring(0, indextel - 1);
                dataStdTel = caltel.substring(indextel, indextel + 10);
                dataStdEmail = caltel.substring(indextel + 11);

                showActionsDialog(pos);
                return true;

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySQLConnect.sentData(txtStdid.getText().toString(), txtStdName.getText().toString(), txtStdTel.getText().toString(), txtStdEmail.getText().toString());
                items.add(txtStdid.getText().toString(), txtStdName.getText().toString(), txtStdTel.getText().toString(), txtStdEmail.getText().toString());
                adt.notifyDataSetChanged();
                txtStdid.setText("");
                txtStdName.setText("");
                txtStdTel.setText("");
                txtStdEmail.setText("");
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickList();
            }
        });

    }

    public void showActionsDialog (final int position) {
        AlertDialog.Buider builder = new AlertDialog.Builder(this);

        builder.setMassage("ตัวเล์อก");

        builder.setPositiveButton("แก้ไข", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showStudentDialog(dataStdId, dataStdName, dataStdTel, dataStdEmail, position);
            }
        });

        builder.setNegativeeButton("ลบ", new DialogInterface.OnClicklListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mySQLConnect.delete_data(dataStdId);
                items.remove(position);
                dataView.setAdapter(adt);
            }
        });
        builder.show();
    }



    public void showStudentDialog (final String StdId, final String StdName , final String StdTel ,final String StdEmail , final int pos ) {
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        promptView = layoutInflater.inflate(R.layout.content_dialog, null);
        AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuider.setView(promptView);

        editStdId = promptView.findViewById(R.id.txtStdId);
        editStdName = promptView.findViewById(R.id.txtStdName);
        editStdTel = promptView.findViewById(R.layout.txtStdTel);
        editStdEmail = promptView.findViewById(R.loyout.txtStdEmail);

        editStdid.setText(StdId);
        editStdName.setText(StdName);
        editStdTel.setText(StdTel);
        editStdEmail.setText(StdEmail);

        alertDialogBuider.setCancelable(false)
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mySQlConnect.upd_data(editStdId.getText().toString(), editStdName.getText().toString(), editStdTel.getText().toString(), editStdEmail.getText().toString());
                        items.set(pos, editStdid.getText().toString() + "\n" + editStdName.getText().toString() + "\n"editStdTel.getText().toString() + "\n" + editStdEmail.getText().toString());
                        dataView.setAdapter(adt);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuider.create();
        alert.show();

        public void update() {
            items = mySQLConnect.getData();
            adt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            dataView.setAdapter(adt);
        }

        public void checkList() {
            Intent refresh = new Intent(MainActivity.this, MainActivity.class);
            finish();
        }

        public void init() {
            txtStdid = findViewById(R.id.txtId);
            txtStdName = findViewById(R.id.txtName);
            txtStdTel = findViewById(R.id.txtTel);
            txtStdEmail = findViewById(R.id.txtEmail);
            btnSave = findViewById(R.id.btnSave);
            btnShow = findViewById(R.id.btnShow);
            dataView = findViewById(R.id.dataView);
            mySQLConnect = new MySQLConnect(MainActivity.this);
        }
    }
}