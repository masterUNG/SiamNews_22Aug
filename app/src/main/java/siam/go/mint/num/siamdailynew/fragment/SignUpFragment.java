package siam.go.mint.num.siamdailynew.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import siam.go.mint.num.siamdailynew.R;
import siam.go.mint.num.siamdailynew.manage.GetAllData;
import siam.go.mint.num.siamdailynew.manage.MyAlert;
import siam.go.mint.num.siamdailynew.manage.MyConstant;
import siam.go.mint.num.siamdailynew.manage.PostNewMember;

/**
 * Created by Tong on 15/8/2560.
 */

public class SignUpFragment extends Fragment {

    //Explicit
    private String nameString, surnameString, emailString,
            userString, passwordString, genderString, divisionString, rePasswordString;
    private boolean aBoolean = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return view;
    }   // onCreateView

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Back Controller
        backController();

        //Save Controller
        saveController();

        //Gender Controller
        genderController();

        //Divition Controller
        divitionController();

    }   // onActivityCreate

    private void divitionController() {

        Spinner spinner = getView().findViewById(R.id.spnDivition);
        MyConstant myConstant = new MyConstant();
        String tag = "22AugV1";

        try {

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlfecdep());
            String strJSoN = getAllData.get();
            Log.d(tag, "JSON ==> " + strJSoN);

            JSONArray jsonArray = new JSONArray(strJSoN);
            final String[] divisionStrings = new String[jsonArray.length()];
            final String[] idStrings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idStrings[i] = jsonObject.getString("fd_id");
                divisionStrings[i] = jsonObject.getString("fd_nameth");
                Log.d(tag, "division[" + i + "] ==> " + divisionStrings[i]);
            }   // for

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    divisionStrings
            );
            spinner.setAdapter(stringArrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    divisionString = idStrings[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    divisionString = idStrings[0];
                }
            });

        } catch (Exception e) {
            Log.d(tag, "e divition ==> " + e.toString());
        }

    }   // divitionController

    private void genderController() {
        RadioGroup radioGroup = getView().findViewById(R.id.ragGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                aBoolean = false;
                switch (i) {
                    case R.id.radMale:
                        genderString = "Male";
                        break;
                    case R.id.radFemale:
                        genderString = "Female";
                        break;
                }
            }
        });
    }


    private void saveController() {
        ImageView imageView = getView().findViewById(R.id.imvSave);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Initial EditText
                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText surNameEditText = getView().findViewById(R.id.edtSurName);
                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);
                EditText RePasswordEditText = getView().findViewById(R.id.edtRePassword);

                //Get Value to String
                nameString = nameEditText.getText().toString().trim();
                surnameString = surNameEditText.getText().toString().trim();
                emailString = emailEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                rePasswordString = RePasswordEditText.getText().toString().trim();

                //Check Space
                if (checkSpace()) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getResources().getString(R.string.have_space),
                            getResources().getString(R.string.message_have_space));
                } else if (!passwordString.equals(rePasswordString)) {
                    //Not Math Password
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getString(R.string.title_not_math),
                            getString(R.string.message_not_math));
                } else if (aBoolean) {
                    //Non Choose Gener
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getString(R.string.title_non_choose),
                            getString(R.string.message_non_choose));
                } else {
                    uploadNewUserToServer();
                }


            }   // onClick
        });
    }

    private void uploadNewUserToServer() {

        //Show Log
        String tag = "22AugV2";
        Log.d(tag, nameString);
        Log.d(tag, surnameString);
        Log.d(tag, genderString);
        Log.d(tag, emailString);
        Log.d(tag, divisionString);
        Log.d(tag, userString);
        Log.d(tag, passwordString);

        MyConstant myConstant = new MyConstant();


        try {

            PostNewMember postNewMember = new PostNewMember(getActivity());
            postNewMember.execute(nameString, surnameString, genderString,
                    emailString, userString, passwordString,
                    divisionString, myConstant.getUrlAddMember());
            String result = postNewMember.get();
            Log.d(tag, "Result ==> " + result);

            if (Boolean.parseBoolean(result)) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            } else {
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("Cannot Upload", "Please Try Again");
            }


        } catch (Exception e) {
            Log.d(tag, "e upload ==> " + e.toString());
        }


    }   // upload

    private boolean checkSpace() {
        return nameString.equals("") ||
                surnameString.equals("") ||
                emailString.equals("") ||
                userString.equals("") ||
                passwordString.equals("") ||
                rePasswordString.equals("");
    }

    private void backController() {
        ImageView imageView = getView().findViewById(R.id.imvBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });
    }
}   //Main Class
