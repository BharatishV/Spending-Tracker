package app.spendingtracker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/** Home-screen widget: this month's total, with a shortcut to add spendings. */
public class SpendWidget extends AppWidgetProvider {

    private static final String PREFS = "widget";

    static void store(Context c, boolean signedIn, String amount, String month, int count, String name) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
                .putBoolean("in", signedIn)
                .putString("amt", amount)
                .putString("month", month)
                .putInt("count", count)
                .putString("name", name)
                .apply();
    }

    static void refreshAll(Context c) {
        AppWidgetManager m = AppWidgetManager.getInstance(c);
        int[] ids = m.getAppWidgetIds(new ComponentName(c, SpendWidget.class));
        for (int id : ids) update(c, m, id);
    }

    @Override
    public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        for (int id : ids) update(c, m, id);
    }

    private static void update(Context c, AppWidgetManager m, int id) {
        SharedPreferences p = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        RemoteViews v = new RemoteViews(c.getPackageName(), R.layout.widget_spend);

        if (p.getBoolean("in", false)) {
            int n = p.getInt("count", 0);
            v.setTextViewText(R.id.w_month, "Spent in " + p.getString("month", ""));
            v.setTextViewText(R.id.w_amount, p.getString("amt", "₹0"));
            v.setTextViewText(R.id.w_sub, n + (n == 1 ? " entry" : " entries"));
            v.setTextViewText(R.id.w_add, "+ Add spending");
        } else {
            v.setTextViewText(R.id.w_month, "Spending tracker");
            v.setTextViewText(R.id.w_amount, "Sign in");
            v.setTextViewText(R.id.w_sub, "Open the app to start");
            v.setTextViewText(R.id.w_add, "Open app");
        }
        v.setOnClickPendingIntent(R.id.w_root, open(c, null, 0));
        v.setOnClickPendingIntent(R.id.w_add, open(c, "add", 1));
        m.updateAppWidget(id, v);
    }

    private static PendingIntent open(Context c, String tab, int requestCode) {
        Intent i = new Intent(c, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (tab != null) i.putExtra("tab", tab);
        return PendingIntent.getActivity(c, requestCode, i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
