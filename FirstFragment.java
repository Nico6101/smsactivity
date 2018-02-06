package com.rcpl.activitysms;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.widget.TextView;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;


public class FirstFragment extends Fragment implements AdapterView.OnItemClickListener
{
    @SuppressLint("InlinedApi")
    private static final int REQUEST_READ_CONTACTS = 444;
    ListView lv1;
    private OnFragmentInteractionListener mListener;
    ArrayList contactlist;
    Cursor cursor;
    ArrayAdapter adapter;
    String sentNumber;

    public FirstFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_first,container,false);
        lv1 = (ListView) v.findViewById(R.id.listview1);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                getContacts();
            }
        }).start();
        lv1.setOnItemClickListener(this);
        return v;
    }

    private boolean mayRequestContacts()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if(ContextCompat.checkSelfPermission(getContext(),READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)
            return true;
        if(shouldShowRequestPermissionRationale(READ_CONTACTS))
            requestPermissions(new String[]{READ_CONTACTS},REQUEST_READ_CONTACTS);
        else
            requestPermissions(new String[]{READ_CONTACTS},REQUEST_READ_CONTACTS);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode==REQUEST_READ_CONTACTS)
                if(grantResults.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    getContacts();
    }

    private void getContacts()
    {
        if(!mayRequestContacts())
            return;

        contactlist = new ArrayList();

        String phoneNumber;
        String displayName;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri phoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_Contact_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String Number = ContactsContract.CommonDataKinds.Phone.NUMBER;

        StringBuffer output = null;

        ContentResolver cr = getActivity().getContentResolver();

        cursor = cr.query(CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
                output = new StringBuffer();
        }
        cursor.moveToFirst();
        int hasphonenumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

        if(hasphonenumber > 0)
        {
            Cursor phoneCursor = cr.query(phoneCONTENT_URI,null,null,null,null);

            while(phoneCursor.moveToNext())
            {
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Number));
                displayName = phoneCursor.getString(phoneCursor.getColumnIndex(DISPLAY_NAME));
                output.append("\nName : "+displayName);
                if(phoneNumber.indexOf('-')<0)
                {
                    output.append("\nPhone Number : "+phoneNumber+"\n");
                }
            }
            phoneCursor.close();
        }
        contactlist.add(output.toString());

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter = new ArrayAdapter(getActivity(),R.layout.list_item,R.id.text1,contactlist);
                lv1.setAdapter(adapter);
            }
        });


    }



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        try
        {
            mListener = (OnFragmentInteractionListener) getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        sentNumber = (String) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(),MessageActivity.class);
        intent.putExtra("sentNumber",sentNumber);
        startActivity(intent);

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
