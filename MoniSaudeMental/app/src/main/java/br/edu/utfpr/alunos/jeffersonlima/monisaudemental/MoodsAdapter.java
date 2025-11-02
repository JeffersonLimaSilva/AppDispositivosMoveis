package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.MoodLog;

public class MoodsAdapter extends BaseAdapter {
    private Context context;
    private List<MoodLog> listMoods;
    private String[] categories;

    private static class MoodsHolder{
        public TextView textViewValueDescription;
        public TextView textViewValuEmotion;
        public TextView textViewValueIntensity;
        public TextView textViewValueCategory;
    }

    public MoodsAdapter(Context context, List<MoodLog> listMoods) {
        this.context = context;
        this.listMoods = listMoods;
        categories = context.getResources().getStringArray(R.array.categories);
    }

    @Override
    public int getCount() {
        return listMoods.size();
    }

    @Override
    public Object getItem(int position) {
        return listMoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MoodsHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_list_moods, parent, false);
            holder = new MoodsHolder();
            holder.textViewValueDescription = convertView.findViewById(R.id.textViewValueDescription);
            holder.textViewValuEmotion = convertView.findViewById(R.id.textViewValueEmotion);
            holder.textViewValueIntensity = convertView.findViewById(R.id.textViewValueIntensity);
            holder.textViewValueCategory = convertView.findViewById(R.id.textViewValueCategory);

            convertView.setTag(holder);
        }else{
            holder = (MoodsHolder) convertView.getTag();
        }
        MoodLog moodLog = listMoods.get(position);
        holder.textViewValueDescription.setText(moodLog.getDescription());
        holder.textViewValuEmotion.setText(moodLog.getEmotion());
        switch (moodLog.getIntensityEmotion()){
            case Leve:
                holder.textViewValueIntensity.setText(R.string.light);
                break;
            case Moderada:
                holder.textViewValueIntensity.setText(R.string.moderate);
                break;
            case Intensa:
                holder.textViewValueIntensity.setText(R.string.intense);
                break;
        }
        holder.textViewValueCategory.setText(categories[moodLog.getCategoryDay()]);

        return convertView;
    }
}
