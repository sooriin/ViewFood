package es.fdi.tmi.viewfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

/**
 * Fragment used within a TabbedLayout with the purpose of showing a translated photo taken by the user.
 * */
public class TranslatedPhotoFragment extends Fragment
{
    private TextView _textPlaceholder;
    private PhotoView _translatedPhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_translated_photo, container, false);

        _textPlaceholder = v.findViewById(R.id.TranslatedPhotoPlaceholder);
        _translatedPhoto = v.findViewById(R.id.TranslatedPhoto);

        return v;
    }

    /**
     * Sets a photo in the fragment's image view.
     * */
    public void setPhoto(String url)
    {
        _textPlaceholder.setVisibility(View.GONE);
        _translatedPhoto.setVisibility(View.VISIBLE);
        Picasso.get().load(url).into(_translatedPhoto);
    }

    /**
     * Restores the fragment to its default state.
     * Removes the photo from the fragment's image view, and sets a default text in its place.
     * */
    public void clearData()
    {
        _translatedPhoto.setVisibility(View.GONE);
        _textPlaceholder.setVisibility(View.VISIBLE);
        _textPlaceholder.setText(R.string.translation_photo_placeholder);
    }
}