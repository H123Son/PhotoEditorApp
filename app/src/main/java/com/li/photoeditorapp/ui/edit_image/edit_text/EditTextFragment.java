package com.li.photoeditorapp.ui.edit_image.edit_text;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.li.photoeditorapp.R;
import com.li.photoeditorapp.base.BaseFragment;
import com.li.photoeditorapp.databinding.FragmentEditTextBinding;
import com.li.photoeditorapp.model.ItemTextFont;
import com.li.photoeditorapp.ui.edit_image.edit_text.adapter.FontAdapter;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextColorClickListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextFontClickListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnItemTextStrokeColorClickListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnTextDoneListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnTextOpacityChangeListener;
import com.li.photoeditorapp.ui.edit_image.edit_text.callback.OnWritingTextListener;
import java.util.ArrayList;
import java.util.List;

public class EditTextFragment extends BaseFragment<FragmentEditTextBinding> implements BottomNavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {
    private List<ItemTextFont> itemTextFontList;
    private FontAdapter fontAdapter;
    private OnItemTextFontClickListener listener;
    private OnItemTextColorClickListener textColorClickListener;
    private OnItemTextStrokeColorClickListener strokeColorClickListener;
    private OnTextDoneListener onTextDoneListener;
    private OnWritingTextListener onWritingTextListener;
    private OnTextOpacityChangeListener opacityChangeListener;
    private InputMethodManager keyboardManager;

    public EditTextFragment() {

    }

    public EditTextFragment(OnItemTextFontClickListener listener, OnItemTextColorClickListener textColorClickListener
            , OnItemTextStrokeColorClickListener strokeColorClickListener
            , OnTextDoneListener onTextDoneListener, OnWritingTextListener onWritingTextListener,OnTextOpacityChangeListener opacityChangeListener) {
        this.listener = listener;
        this.textColorClickListener = textColorClickListener;
        this.strokeColorClickListener = strokeColorClickListener;
        this.onTextDoneListener = onTextDoneListener;
        this.onWritingTextListener = onWritingTextListener;
        this.opacityChangeListener = opacityChangeListener;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_edit_text;
    }

    @Override
    protected void onViewReady(View view) {
        setUpFontList();
        dataBinding.imgCheck.setOnClickListener(this::onClick);
        dataBinding.imgKeyboard.setOnClickListener(this::onClick);
        dataBinding.navEditText.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        dataBinding.navEditText.setSelectedItemId(R.id.nav_text_color);
        showKeyBoard();
        dataBinding.edtTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = dataBinding.edtTextContent.getText().toString();
                if (content.equals("")){
                    dataBinding.tvTitle.setTextColor(getResources().getColor(R.color.background_app_color,null));
                    onWritingTextListener.onTextChange(content);
                }
                else {
                    dataBinding.tvTitle.setTextColor(getResources().getColor(R.color.image_background_color, null));
                    onWritingTextListener.onTextChange(content);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void showKeyBoard() {
        dataBinding.edtTextContent.requestFocus();
        keyboardManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboardManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void addTextFont() {
        itemTextFontList = new ArrayList<>();
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.roboto_black), "roboto"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.artifact), "Atifact"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.selima), "Selima"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.pecita), "Pecita"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.mono), "Mono"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.russian_amaticsc), "Amatic"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.libre), "Libre"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.report), "Report"));
        itemTextFontList.add(new ItemTextFont(ResourcesCompat.getFont(getContext(), R.font.silkscreen), "Screen"));
    }

    private void setUpFontList() {
        addTextFont();
        fontAdapter = new FontAdapter(itemTextFontList, getLayoutInflater(), listener);
        dataBinding.rvFontList.setHasFixedSize(true);
        dataBinding.rvFontList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        dataBinding.rvFontList.setAdapter(fontAdapter);
    }
    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_edit_text, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_text_color:
                dataBinding.navEditText.getMenu().findItem(R.id.nav_text_color).setChecked(true);
                replaceFragment(new TextColorChangerFragment(textColorClickListener));
                break;
            case R.id.nav_text_stroke:
                dataBinding.navEditText.getMenu().findItem(R.id.nav_text_stroke).setChecked(true);
                replaceFragment(new TextStrokeChangerFragment(strokeColorClickListener));
                break;
            case R.id.nav_text_opacity:
                dataBinding.navEditText.getMenu().findItem(R.id.nav_text_opacity).setChecked(true);
                replaceFragment(new TextOpacityFragment(opacityChangeListener));

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_check:
                String content = dataBinding.edtTextContent.getText().toString();
                if (content.equals("")) {
                    keyboardManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    onTextDoneListener.onTextDone();
                } else {
                    if(checkVisibaleView()){
                        onTextDoneListener.onTextDone();
                    }
                    else {
                        keyboardManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        setVisible();
                    }
                }
                break;

            case R.id.img_keyboard:
                changeText();
                break;
        }

    }

    private void changeText() {
        if(dataBinding.edtTextContent.getVisibility()== View.GONE||dataBinding.edtTextContent.getVisibility()==View.INVISIBLE){
            dataBinding.edtTextContent.setVisibility(View.VISIBLE);
            dataBinding.edtTextContent.requestFocus();
            keyboardManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboardManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            dataBinding.viewLine.setVisibility(View.GONE);
            dataBinding.rvFontList.setVisibility(View.GONE);
            dataBinding.frEditText.setVisibility(View.GONE);
            dataBinding.navEditText.setVisibility(View.GONE);
        }
    }

    private void setVisible() {
        dataBinding.rvFontList.setVisibility(View.VISIBLE);
        dataBinding.viewLine.setVisibility(View.VISIBLE);
        dataBinding.frEditText.setVisibility(View.VISIBLE);
        dataBinding.navEditText.setVisibility(View.VISIBLE);
        dataBinding.tvTitle.setVisibility(View.VISIBLE);
        dataBinding.edtTextContent.setVisibility(View.GONE);
    }

    private boolean checkVisibaleView() {
        if (dataBinding.rvFontList.getVisibility() == View.VISIBLE
                && dataBinding.frEditText.getVisibility() == View.VISIBLE
                && dataBinding.navEditText.getVisibility() == View.VISIBLE)
                           {
            return true;

        } else
            return false;

    }
}
