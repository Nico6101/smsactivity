package com.rcpl.activitysms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment
{

    ListView lv1;
    String[] projection;
    String sentNumber;
    private OnFragmentInteractionListener mListener;

    public SecondFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v1 = inflater.inflate(R.layout.fragment_second, container, false);
        lv1 = (ListView) v1.findViewById(R.id.listView2);
        LoginActivity.database d = new LoginActivity.database(getActivity().getBaseContext());
        SQLiteDatabase db = d.getReadableDatabase();
        projection = new String[]{"loginno","sentno"};
        Cursor cursor = db.query("SMS_DETAILS",projection,null,null,null,null,null);

        MyCursorAdapter adapter = new MyCursorAdapter(getActivity(),cursor,0);
        lv1.setAdapter(adapter);
        db.close();
        return v1;
    }

    class MyCursorAdapter extends CursorAdapter
    {

        public MyCursorAdapter(Context context, Cursor c, int flags)
        {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            return null;
        }


        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            TextView tv1 = (TextView) view.findViewById(R.id.text1);
            sentNumber = cursor.getString(cursor.getColumnIndex("sentno"));
            tv1.append(sentNumber);
        }

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
