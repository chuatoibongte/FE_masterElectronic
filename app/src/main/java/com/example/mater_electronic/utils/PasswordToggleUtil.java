package com.example.mater_electronic.utils;

import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mater_electronic.R;

public class PasswordToggleUtil {
    public static void setupPasswordToggle(EditText passwordField, ImageView toggleIcon) {
        final boolean[] isVisible = {false}; // array to allow modification inside lambda

        toggleIcon.setOnClickListener(v -> {
            isVisible[0] = !isVisible[0];

            if (isVisible[0]) {
                passwordField.setTransformationMethod(SingleLineTransformationMethod.getInstance());
            } else {
                passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            passwordField.setSelection(passwordField.getText().length());

            // Optional: toggle the icon drawable
            toggleIcon.setImageResource(isVisible[0] ? R.drawable.ic_eye : R.drawable.ic_eye_closed);
        });
    }
}

