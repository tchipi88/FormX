package com.appli.nyx.formx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.widget.ImageView;

import com.appli.nyx.formx.di.module.GlideApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageUtils {

    // Prevent instantiation
    private ImageUtils() {

    }

    public static void displayRoundImageFromStorageReference(final Context context, final StorageReference storageReference, final ImageView imageView, Drawable errorDrawable) {
        RequestOptions myOptions = new RequestOptions().centerCrop().dontAnimate();

        GlideApp.with(context).asBitmap().apply(myOptions).load(storageReference).error(errorDrawable).into(new BitmapImageViewTarget(imageView) {

            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * Crops image into a circle that fits within the ImageView.
     */
    public static void displayRoundImageFromUrl(final Context context, final String url, final ImageView imageView) {
        RequestOptions myOptions = new RequestOptions().centerCrop().dontAnimate();

        Glide.with(context).asBitmap().apply(myOptions).load(url).into(new BitmapImageViewTarget(imageView) {

            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void displayImageFromUrl(final Context context, final String url, final ImageView imageView) {
        displayImageFromUrl(context, url, imageView, null, null);
    }

    public static void displayImageFromUrl(final Context context, final String url, final ImageView imageView, Drawable placeholderDrawable) {
        displayImageFromUrl(context, url, imageView, placeholderDrawable, null);
    }

    /**
     * Displays an image from a URL in an ImageView.
     */
    public static void displayImageFromUrl(final Context context, final String url, final ImageView imageView, Drawable placeholderDrawable, RequestListener listener) {
        RequestOptions myOptions = new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(placeholderDrawable);

        if (listener != null) {
            Glide.with(context).load(url).apply(myOptions).listener(listener).into(imageView);
        } else {
            Glide.with(context).load(url).apply(myOptions).listener(listener).into(imageView);
        }
    }

    public static void displayRoundImageFromUrlWithoutCache(final Context context, final String url, final ImageView imageView) {
        displayRoundImageFromUrlWithoutCache(context, url, imageView, null);
    }

    public static void displayRoundImageFromUrlWithoutCache(final Context context, final String url, final ImageView imageView, RequestListener listener) {
        RequestOptions myOptions = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

        if (listener != null) {
            Glide.with(context).asBitmap().load(url).apply(myOptions).listener(listener).into(new BitmapImageViewTarget(imageView) {

                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            Glide.with(context).asBitmap().load(url).apply(myOptions).into(new BitmapImageViewTarget(imageView) {

                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }

    /**
     * Displays an image from a URL in an ImageView.
     * If the image is loading or nonexistent, displays the specified placeholder image instead.
     */
    public static void displayImageFromUrlWithPlaceHolder(final Context context, final String url, final ImageView imageView, int placeholderResId) {
        RequestOptions myOptions = new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(placeholderResId);

        Glide.with(context).load(url).apply(myOptions).into(imageView);
    }

    /**
     * Displays an image from a URL in an ImageView.
     */
    public static void displayGifImageFromUrl(Context context, String url, ImageView imageView, Drawable placeholderDrawable, RequestListener listener) {
        RequestOptions myOptions = new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(placeholderDrawable);

        if (listener != null) {
            Glide.with(context).asGif().load(url).apply(myOptions).listener(listener).into(imageView);
        } else {
            Glide.with(context).asGif().load(url).apply(myOptions).into(imageView);
        }
    }

    public static void displayGifImageFromUrl(Context context, String url, ImageView imageView, String thumbnailUrl) {
        displayGifImageFromUrl(context, url, imageView, thumbnailUrl, null);
    }

    /**
     * Displays an GIF image from a URL in an ImageView.
     */
    public static void displayGifImageFromUrl(Context context, String url, ImageView imageView, String thumbnailUrl, Drawable placeholderDrawable) {
        RequestOptions myOptions = new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(placeholderDrawable);

        if (thumbnailUrl != null) {
            Glide.with(context).asGif().load(url).apply(myOptions).thumbnail(Glide.with(context).asGif().load(thumbnailUrl)).into(imageView);
        } else {
            Glide.with(context).asGif().load(url).apply(myOptions).into(imageView);
        }
    }


    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

	public static File compressImage(File imageFile, int reqWidth, int reqHeight,
									 Bitmap.CompressFormat compressFormat, int quality, String destinationPath)
            throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(destinationPath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            // write the compressed bitmap at the destination specified by destinationPath.
            decodeSampledBitmapFromFile(imageFile, reqWidth, reqHeight).compress(compressFormat, quality,
                    fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }

	public static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight)
            throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //check the rotation of the image and display it properly
        ExifInterface exif;
        exif = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }
        scaledBitmap =
                Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                        matrix, true);
        return scaledBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}