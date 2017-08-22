package siam.go.mint.num.siamdailynew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import siam.go.mint.num.siamdailynew.R;
import siam.go.mint.num.siamdailynew.ServiceActivity;
import siam.go.mint.num.siamdailynew.manage.GetAllData;
import siam.go.mint.num.siamdailynew.manage.MyAlert;
import siam.go.mint.num.siamdailynew.manage.MyConstant;

/**
 * Created by Tong on 15/8/2560.
 */

public class MainFragment extends Fragment{

    private String userString, passwordStrig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        return view;
    }   // Create View


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Register Controller
        registerController();

        //Login Controller
        loginController();

    } // OnActivityCreate

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Inital View to Variable
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                //Change Type Value
                // Get value From Edit text การดึงค่าไปทำงานต่อ
                userString = userEditText.getText().toString().trim();
                passwordStrig = passwordEditText.getText().toString().trim();

                //การเช็ค Space
                if (userString.equals("") || passwordStrig.equals("")) {
                    // Have Space
                    Log.d("15Aug", "Have Space");

                    //Call MyAlert
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getString(R.string.have_space),
                            getString(R.string.message_have_space));

                } else {
                    // No Space
                    Log.d("15Aug", "No Space");
                    checkUserAndPass();

                }


            }   //OnClick
        });
    }

    private void checkUserAndPass() {

        String tag = "22AugV3";
        MyConstant myConstant = new MyConstant();
        boolean b = true; // True ==> User False
        String[] columnStrings = myConstant.getColumnMemberStrings();

        ArrayList<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("m_id");
        for (int i=0; i<columnStrings.length; i+=1) {
            stringArrayList.add(columnStrings[i]);
        }   // for
        Log.d(tag, "arrayList ==> " + stringArrayList);
        String[] fullColumnStrings = stringArrayList.toArray(new String[0]);

        String[] userStrings = new String[fullColumnStrings.length];

        try {

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetAllMember());
            String strJSON = getAllData.get();
            Log.d(tag, "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            String tag1 = "22AugV4";
            Log.d(tag1, "userString ==> " + userString);
            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d(tag1, "jsonObject[" + i + "] ==> " + jsonObject.getString(fullColumnStrings[5]));
                if (userString.equals(jsonObject.getString(fullColumnStrings[5]))) {

                    //User True
                    b = false;
                    for (int i1=0; i1<fullColumnStrings.length; i1+=1) {
                        userStrings[i1] = jsonObject.getString(fullColumnStrings[i1]);
                        Log.d("22AugV5", "userString[" + i1 + "] ==> " + userStrings[i1]);
                    }

                }   // if

            }   // for

            if (b) {
                //User False
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("User False", "No " + userString + " in my Database");
            } else if (passwordStrig.equals(userStrings[6])) {
                //Password True
                Toast.makeText(getActivity(), "Welcome " + userStrings[1], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                intent.putExtra("User", userStrings);
                getActivity().startActivity(intent);
                getActivity().finish();
            } else {
                //Password False
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("Password False", "Please Try Again Password False");
            }


        } catch (Exception e) {
            Log.d(tag, "e check ==> " + e.toString());
        }

    }   // checkUser

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Replace Fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();

            }   //OnClick
        });
    }
}// Main Class