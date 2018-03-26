package project.sialkot.task2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hamza on 3/19/2018.
 */

class UserAdapter extends ArrayAdapter<User> {
    public ArrayList<User> getOListuser() {
        return OListuser;
    }

    public void setOListuser(ArrayList<User> OListuser) {
        this.OListuser = OListuser;
    }

    public LayoutInflater getOLayoutInflater() {
        return OLayoutInflater;
    }

    public void setOLayoutInflater(LayoutInflater OLayoutInflater) {
        this.OLayoutInflater = OLayoutInflater;
    }

    ArrayList<User> OListuser;
    LayoutInflater OLayoutInflater;

    public UserAdapter(@NonNull Context context, ArrayList<User> OuserList) {
        super(context, 0, OuserList);
        OListuser = OuserList;
        OLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return OListuser.size();
    }

    @Override
    public User getItem(int position) {

        return OListuser.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = OLayoutInflater.inflate(R.layout.listviews, null);
        TextView weather=(TextView)view.findViewById(R.id.name);
        TextView condition=(TextView)view.findViewById(R.id.number);
        User ouser=  getItem(position);
        weather.setText(ouser.getName());
        condition.setText(ouser.getContact());
        return view;


    }
}