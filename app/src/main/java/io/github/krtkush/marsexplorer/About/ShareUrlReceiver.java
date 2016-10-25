package io.github.krtkush.marsexplorer.About;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.github.krtkush.marsexplorer.MarsExplorerApplication;
import io.github.krtkush.marsexplorer.R;

/**
 * Created by kartikeykushwaha on 25/10/16.
 */

public class ShareUrlReceiver extends BroadcastReceiver {

    /**
     * Broadcast receiver to show the share pop-up.
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getDataString();

        if (url != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);

            Intent chooserIntent = Intent.createChooser(shareIntent,
                    MarsExplorerApplication.getApplicationInstance().getString(R.string.share));
            chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(chooserIntent);
        }
    }
}
