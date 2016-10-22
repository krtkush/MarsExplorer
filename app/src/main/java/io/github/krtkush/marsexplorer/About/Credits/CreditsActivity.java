package io.github.krtkush.marsexplorer.About.Credits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.krtkush.marsexplorer.R;
import timber.log.Timber;

public class CreditsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    private CreditsActivityPresenterInteractor presenterInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        // Initialise butterknife, timber and the presenter layer
        ButterKnife.bind(CreditsActivity.this);
        Timber.tag(CreditsActivity.this.getClass().getSimpleName());
        presenterInteractor = new CreditsActivityPresenterLayer(this);

        // Setup the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
