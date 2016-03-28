package ua.kpi.infosecurity.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import ua.kpi.infosecurity.R;
import ua.kpi.infosecurity.model.FileObj;
import ua.kpi.infosecurity.model.Permission;

/**
 * Created on 3/28/16.
 */
public class FilesAdapter extends ArrayAdapter<FileObj> {

    private Context context;
    private String userId;

    public FilesAdapter(Context context, List<FileObj> objects, String userId) {
        super(context, 0, objects);
        this.userId = userId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.file_layout, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder vh = (ViewHolder) convertView.getTag();
        FileObj file = getItem(position);

        if (file.isFile()) {
            vh.image.setImageResource(R.drawable.cat_icon);
        } else {
            vh.image.setImageResource(R.drawable.folder_icon);
        }

        vh.nameTextView.setText(file.getName());

        vh.accessTextView.setText(getAccessString(file));

        return convertView;
    }

    private String getAccessString(FileObj file) {
        Permission permission = Realm.getDefaultInstance()
                .where(Permission.class)
                .equalTo("userId", userId)
                .equalTo("fileId", file.getUuid())
                .findFirst();
        if (permission == null) {
            return "-";
        } else {
            return permission.getLevel();
        }
    }

    class ViewHolder {

        @Bind(R.id.file_layout_name)
        TextView nameTextView;
        @Bind(R.id.file_layout_access)
        TextView accessTextView;
        @Bind(R.id.file_layout_image)
        ImageView image;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
