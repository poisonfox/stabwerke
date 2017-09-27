package com.sissimon.gitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnTouchListener {


    // Zu definierende Variabeln:
        RelativeLayout gesamt;

    private DragController mDragController;
    private DragLayer mDraglayer;
    final Context context = this;
    private Vibrator mVibrator;


    public boolean haken = false;
    private boolean Da = false;

    private ArrayList<String> jointlist = new ArrayList<String>(88 * 14);
    private ArrayList<String> jointlist2;
    private ArrayList<String> elementlist = new ArrayList<String>(88 * 14);
    private ArrayList<String> elementlist2;
    private ArrayList<String> Stabliste;
    private ArrayList<String> Vergleich;
    private ArrayList<String> loesungsArray;
    private ArrayList<String> Nullstaebe;
    public  ArrayList<ImageView> JointView = new ArrayList<>();
    public  ArrayList<TextView> NumberView = new ArrayList<>();
    private ArrayList<String> NullstabErgebnis;

    public double[][] AMat;
    private double[][] FVek;
    private double[][] XVek;
    private boolean tablet;

    private int number;
    public int statBest;
    private boolean stat_best;
    public int dimKraft;
    public int jointZaehler;
    public int lagerreaktionen;

    public String choice = "";

    //public static final String SOME_KEY = "some_key";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDragController = new DragController(this);
        setupViews();
        gesamt = (RelativeLayout) findViewById(R.id.gesamt);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;

        float dpX = outMetrics.widthPixels / density;

        if(dpX > 605 ){
            tablet = true;
        }
        else {
            tablet = false;
        }

        //Button Neues Blatt
        final ImageButton NewBtn = (ImageButton) findViewById(R.id.button_newfile);
        NewBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Neue Seite erstellen?");


                alertDialogBuilder
                        .setMessage("Bisherige Inhalte werden dabei gelöscht.")
                        .setCancelable(false)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Intent myIntent = getIntent();
                                finish();
                                startActivity(myIntent);
                            }
                        })

                        .setNegativeButton("Nein", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        //Button Festlager
        final ImageButton btnOpenPopup_festlager = (ImageButton) findViewById(R.id.button_festlager);
        btnOpenPopup_festlager.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                LayoutInflater layoutInflater_2
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                final View popupView = layoutInflater.inflate(R.layout.popup_fest, null);

                final View Linear = layoutInflater_2.inflate(R.layout.activity_main, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT);


                ImageButton btnDismiss_festlager = (ImageButton) popupView.findViewById(R.id.dismiss_festlager);
                btnDismiss_festlager.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(btnOpenPopup_festlager, Gravity.TOP | Gravity.RIGHT, 0, 0);

                //

                ImageButton add_festlager_1 = (ImageButton) popupView.findViewById(R.id.festlager_1);
                final RelativeLayout Start_f = (RelativeLayout) Linear.findViewById(R.id.gesamt);

                add_festlager_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_festlager_1(Start_f);
                        popupWindow.dismiss();

                    }
                });

                ImageButton add_festlager_2 = (ImageButton) popupView.findViewById(R.id.festlager_2);

                add_festlager_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_festlager_2(Start_f);
                        popupWindow.dismiss();

                    }
                });

            }
        });

        //Button Loslager
        final ImageButton btnOpenPopup_loslager = (ImageButton) findViewById(R.id.button_loslager);
        btnOpenPopup_loslager.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                LayoutInflater layoutInflater_2
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = layoutInflater.inflate(R.layout.popup_los, null);
                final View Linear = layoutInflater_2.inflate(R.layout.activity_main, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT);

                ImageButton btnDismiss_loslager = (ImageButton) popupView.findViewById(R.id.dismiss_loslager);
                btnDismiss_loslager.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();

                    }


                });

                popupWindow.showAtLocation(btnOpenPopup_loslager, Gravity.TOP | Gravity.RIGHT, 0, 0);

                //

                final RelativeLayout Start_f = (RelativeLayout) Linear.findViewById(R.id.gesamt);

                ImageButton add_loslager_1 = (ImageButton) popupView.findViewById(R.id.loslager_1);


                add_loslager_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {


                        addImageView_loslager_1(Start_f);
                        popupWindow.dismiss();

                    }
                });


                ImageButton add_loslager_2 = (ImageButton) popupView.findViewById(R.id.loslager_2);

                add_loslager_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_loslager_2(Start_f);
                        popupWindow.dismiss();

                    }
                });


                ImageButton add_loslager_3 = (ImageButton) popupView.findViewById(R.id.loslager_3);

                add_loslager_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_loslager_3(Start_f);
                        popupWindow.dismiss();

                    }
                });

                ImageButton add_loslager_4 = (ImageButton) popupView.findViewById(R.id.loslager_4);

                add_loslager_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_loslager_4(Start_f);
                        popupWindow.dismiss();

                    }
                });
            }

        });

        //Button Stäbe OnClick --> Popup Window
        final ImageButton btnOpenPopup_stab = (ImageButton) findViewById(R.id.button_stab);
        btnOpenPopup_stab.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                LayoutInflater layoutInflater_2
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = layoutInflater.inflate(R.layout.popup_stab, null);
                final View Linear = layoutInflater_2.inflate(R.layout.activity_main, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT);

                ImageButton btnDismiss_stab = (ImageButton) popupView.findViewById(R.id.dismiss_stab);
                btnDismiss_stab.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(btnOpenPopup_stab, Gravity.TOP | Gravity.RIGHT, 0, 0);

                //

                final RelativeLayout Start_f = (RelativeLayout) Linear.findViewById(R.id.gesamt);

                ImageButton add_stab_1 = (ImageButton) popupView.findViewById(R.id.stab_1);
                add_stab_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_stab_1(Start_f);
                        popupWindow.dismiss();
                    }
                });

                ImageButton add_stab_2 = (ImageButton) popupView.findViewById(R.id.stab_2);
                add_stab_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_stab_2(Start_f);
                        popupWindow.dismiss();
                    }
                });

                ImageButton add_stab_3 = (ImageButton) popupView.findViewById(R.id.stab_3);
                add_stab_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_stab_3(Start_f);
                        popupWindow.dismiss();
                    }
                });

                ImageButton add_stab_4 = (ImageButton) popupView.findViewById(R.id.stab_4);
                add_stab_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        addImageView_stab_4(Start_f);
                        popupWindow.dismiss();
                    }
                });

            }
        });

        //Button Kräfte
        final ImageButton btnOpenPopup_kraft = (ImageButton) findViewById(R.id.button_kraft);
        btnOpenPopup_kraft.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                LayoutInflater layoutInflater_2
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = layoutInflater.inflate(R.layout.popup_kraft, null);
                final View Linear = layoutInflater_2.inflate(R.layout.activity_main, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT);

                ImageButton btnDismiss_kraft = (ImageButton) popupView.findViewById(R.id.dismiss_kraft);
                btnDismiss_kraft.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(btnOpenPopup_kraft, Gravity.TOP | Gravity.RIGHT, 0, 0);

                //

                final RelativeLayout Start_f = (RelativeLayout) Linear.findViewById(R.id.gesamt);

                ImageButton add_kraft_1 = (ImageButton) popupView.findViewById(R.id.kraft_1);
                add_kraft_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_kraft_1(Start_f);
                        popupWindow.dismiss();
                    }
                });

                ImageButton add_kraft_2 = (ImageButton) popupView.findViewById(R.id.kraft_2);
                add_kraft_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_kraft_2(Start_f);
                        popupWindow.dismiss();
                    }
                });

                ImageButton add_kraft_3 = (ImageButton) popupView.findViewById(R.id.kraft_3);
                add_kraft_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_kraft_3(Start_f);
                        popupWindow.dismiss();
                    }
                });

                ImageButton add_kraft_4 = (ImageButton) popupView.findViewById(R.id.kraft_4);
                add_kraft_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {

                        addImageView_kraft_4(Start_f);
                        popupWindow.dismiss();
                    }
                });

            }
        });

        //Button Solve
        final ImageButton btnOpenPopup_solve = (ImageButton) findViewById(R.id.button_solve);
        btnOpenPopup_solve.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                haken = true;
                Da = false;

                int i;

                for (i = 0; i < 14 * 88; i++) {
                    if (mDragController.elementList.get(i) == "y") {
                        Da = true;
                        break;
                    }
                    if (i == 14 * 88 - 1) {
                        haken = false;
                    }
                }

                if (Da) {
                    final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.height = WindowManager.LayoutParams.MATCH_PARENT;
                    params.gravity = Gravity.TOP | Gravity.RIGHT;
                    dialog.getWindow().setAttributes(params);
                    dialog.setContentView(R.layout.popup_fertig);
                    dialog.setCancelable(false);

                    ImageButton button_dismiss = (ImageButton) dialog.findViewById(R.id.dismiss_solve);
                    button_dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            haken = false;
                            dialog.dismiss();
                            DeleteJoints();
                        }
                    });

                    dialog.show();


                    setJoint();
                    CreateXandBVektor();


                    if (statBest == 0) {

                        stat_best = true;
                        setMatrizen();
                        FindNullstaebe();
                        SolveLGS();
                    }
                    else{
                        makeToast("Statisch unbestimmt!!!");
                    }
                     if (statBest == 0 && stat_best == true){
                        makeToast("Statisch bestimmt");

                        final ImageButton button_quiz = (ImageButton) dialog.findViewById(R.id.quiz);
                        button_quiz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Toast.makeText(MainActivity.this, "Welches sind die OFFENSICHTLICHEN Nullstäbe?", Toast.LENGTH_LONG).show();
                                NullstabQuiz();
                            }

                        });

                        ImageButton button_loesung = (ImageButton) dialog.findViewById(R.id.loesung);
                        button_loesung.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Loesung();
                            }
                        });

                    }
                    else {
                        makeToast("Statisch unbestimmt!!!");
                    }

                }
            }
        });
    }


    public boolean startDrag(View v, MotionEvent ev) {

        Object dragInfo = v;
        if (v.getVisibility() == View.VISIBLE) {
            mDragController.startDrag(choice, mDraglayer, v, dragInfo, DragController.DRAG_ACTION_MOVE, ev,gesamt);
        }

        return true;
    }

    public boolean onTouch(View v, MotionEvent ev) {

        return true;
    }


    //----- Funktionen Bilder hinzufügen ++ Drag/Drop

    // Festlager 1
    private void addImageView_festlager_1(View view) {
        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_festlager_1);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);

        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Festlager 1";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });

    }

    // Festlager 2
    private void addImageView_festlager_2(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_festlager_2);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Festlager 2";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Loslager 1
    private void addImageView_loslager_1(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_loslager_1);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams((int)( RelativeLayout.LayoutParams.WRAP_CONTENT), (int)(RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setLayoutParams(festlager1_layout_parameter);
       // imageView.set
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Loslager 1";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }

                }
                return handledHere;
            }
        });
    }

    // Loslager 2
    private void addImageView_loslager_2(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_loslager_2);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Loslager 2";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Loslager 3
    private void addImageView_loslager_3(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_loslager_3);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Loslager 3";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Loslager 4
    private void addImageView_loslager_4(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_loslager_4);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Loslager 4";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Kraft 1
    private void addImageView_kraft_1(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_kraft_1);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Kraft 1";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Kraft 2
    private void addImageView_kraft_2(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_kraft_2);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Kraft 2";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Kraft 3
    private void addImageView_kraft_3(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_kraft_3);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Kraft 3";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Kraft 4
    private void addImageView_kraft_4(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bild_kraft_4);
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Kraft 4";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Stab 1
    private void addImageView_stab_1(View view) {
        ImageView imageView = new ImageView(this);
        if(tablet==false){
            imageView.setImageResource(R.drawable.bild_stab_1);
        }else {
            imageView.setImageResource(R.drawable.bild_stab1_tablet);
        }
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Stab 1";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Stab 2
    private void addImageView_stab_2(View view) {
        ImageView imageView = new ImageView(this);
        if(tablet==false){
            imageView.setImageResource(R.drawable.bild_stab_2);
        }else {
            imageView.setImageResource(R.drawable.bild_stab2_tablet);
        }
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Stab 2";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });


    }

    // Stab 3
    private void addImageView_stab_3(View view) {
        ImageView imageView = new ImageView(this);
        if(tablet==false){
            imageView.setImageResource(R.drawable.bild_stab_3);
        }else {
            imageView.setImageResource(R.drawable.bild_stab3_tablet);
        }
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Stab 3";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Stab 4
    private void addImageView_stab_4(View view) {
        ImageView imageView = new ImageView(this);
        if(tablet==false){
            imageView.setImageResource(R.drawable.bild_stab_4);
        }else {
            imageView.setImageResource(R.drawable.bild_stab4_tablet);
        }
        RelativeLayout.LayoutParams festlager1_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(festlager1_layout_parameter);
        mDraglayer.addView(imageView);


        imageView.setOnTouchListener(new ImageView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                boolean handledHere = false;
                if (haken == false) {
                    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                        choice = "Stab 4";
                        handledHere = startDrag(v, ev);
                        if (handledHere) {
                            v.performClick();

                        }

                    }
                }
                return handledHere;

            }

        });
    }

    // Zeug
    private void setupViews() {
        DragController dragController = mDragController;
        mDraglayer = (DragLayer) findViewById(R.id.drag_layer);
        mDraglayer.setDragController(dragController);
        dragController.addDropTarget(mDraglayer);
        mDragController.setElementList();
    }

    // Ermitteln der nötigen Gelenke, setze Gelenke
    private void setJoint() {
        jointlist2 = new ArrayList<>();
        jointlist = mDragController.jointList;
        elementlist = mDragController.elementList;
        float height = mDraglayer.getHeight();
        float width = mDraglayer.getWidth();
        int zaehler = 0;
        jointZaehler = 0;

        for (int i = 0; i < 88; i++) {

            if (elementlist.get(i) == "y" || elementlist.get(i + 88) == "y" || elementlist.get(i + 88*2) == "y"
                    || elementlist.get(i + 88*3) == "y" || elementlist.get(i + 88*4) == "y" || elementlist.get(i + 88*5) == "y") {

                Integer ii = i;
                jointlist2.add(zaehler, ii.toString());
                zaehler++;
            } else if (jointlist.get(i) == "y") {

                int x = i % 11;
                int y = i / 11;
                int breite;
                int hoehe;
                if (tablet == false) {
                    breite = (int) (((52.0 + (x * 80)) / mDragController.dpX) * width);
                    hoehe = (int) (((52.0 + (y * 80)) / mDragController.dpY) * height);
                }
                else
                {
                    breite = (int) (((72.0 + (x * 100)) / mDragController.dpX) * width);
                    hoehe = (int) (((72.0 + (y * 100)) / mDragController.dpY) * height);
                }

                RelativeLayout ge = (RelativeLayout) findViewById(R.id.gesamt);
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.gelenk_neu);
                JointView.add(jointZaehler, imageView);
                RelativeLayout.LayoutParams gelenk_layout_parameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                gelenk_layout_parameter.leftMargin = breite;
                gelenk_layout_parameter.topMargin = hoehe;
                ge.addView(JointView.get(jointZaehler), gelenk_layout_parameter);
                Integer ii = i;
                jointlist2.add(zaehler, ii.toString());
                zaehler++;
                jointZaehler++;

            }

        }

    }

    // Erstelle den x- und b- Vektor für LGS A*x=b
    private void CreateXandBVektor() {

        dimKraft = 0;
        elementlist2 = new ArrayList<>();
        float height = mDraglayer.getHeight();
        float width = mDraglayer.getWidth();
        int i;
        int j;
        int x;
        int y;
        int zaehler = 0;
        Integer nummer = 0;

        for (j = 0; j < 14; j++) {
            for (i = 0; i < 88; i++) {

                Integer ii = j * 88 + i;
                if (elementlist.get(ii) == "y") {

                    if (j < 10) {
                        RelativeLayout ge = (RelativeLayout) findViewById(R.id.gesamt);
                        TextView textView = new TextView(this);
                        x = i % 11;
                        y = i / 11;

                        nummer = zaehler + 1;
                        textView.setText(nummer.toString());
                        textView.setTextColor(getResources().getColor(R.color.green3));
                        textView.setTextSize(20);
                        RelativeLayout.LayoutParams textView_parameter = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        float dpX= mDragController.dpX;
                        float dpY= mDragController.dpY;
                        int breite;
                        int hoehe;
                        if(tablet == false) {
                            breite = (int) (((52.0 + (x * 80)) / dpX) * width);
                            hoehe = (int) (((52.0 + (y * 80)) / dpY) * height);
                        }
                        else
                        {
                            breite = (int) (((72.0 + (x * 100)) / dpX) * width);
                            hoehe = (int) (((72.0 + (y * 100)) / dpY) * height);
                        }

                        if (j == 0 || j == 2) {
                            breite = (int) (breite - 15 * width / dpX);
                            hoehe = (int) (hoehe + 5 * height / dpY);
                        }
                        switch (j) {
                            case 1:
                                breite = (int) (breite - 7 * width / dpX);
                                hoehe = (int) (hoehe + 12 * height / dpY);
                                break;
                            case 3:
                                breite = (int) (breite + 13 * width / dpX);
                                hoehe = (int) (hoehe + 14 * height / dpY);
                                break;
                            case 4:
                                breite = (int) (breite - 5 * width / dpX);
                                hoehe = (int) (hoehe + 13 * height / dpY);
                                break;
                            case 5:
                                breite = (int) (breite + 14 * width / dpX);
                                hoehe = (int) (hoehe + 13 * height / dpY);
                                break;
                            case 6:
                                breite = (int) (breite + 45 * width / dpX);
                                hoehe = (int) (hoehe - 60 * height / dpY);
                                break;
                            case 7:
                                breite = (int) (breite - 30 * width / dpX);
                                hoehe = (int) (hoehe - 52 * height / dpY);
                                break;
                            case 8:
                                breite = (int) (breite + 42 * width / dpX);
                                hoehe = (int) (hoehe - 17 * height / dpY);
                                break;
                            case 9:
                                breite = (int) (breite + 11 * width / dpX);
                                hoehe = (int) (hoehe - 40 * height / dpY);
                                break;
                        }
                        textView_parameter.leftMargin = breite;
                        textView_parameter.topMargin = hoehe;
                        NumberView.add(zaehler, textView);
                        ge.addView(NumberView.get(zaehler), textView_parameter);
                    }

                    elementlist2.add(zaehler, ii.toString());
                    zaehler++;

                    if (j < 2) {
                        ii = (j + 14) * 88 + i;
                        elementlist2.add(zaehler, ii.toString());

                        RelativeLayout ge = (RelativeLayout) findViewById(R.id.gesamt);
                        TextView textView = new TextView(this);
                        x = i % 11;
                        y = i / 11;

                        nummer = zaehler + 1;
                        textView.setText(nummer.toString());
                        textView.setTextColor(getResources().getColor(R.color.green3));
                        textView.setTextSize(20);
                        RelativeLayout.LayoutParams textView_parameter = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        int breite;
                        int hoehe;
                        if(tablet == false) {
                            breite = (int) (((45.0 + (x * 80)) / mDragController.dpX) * width);
                            hoehe = (int) (((77.0 + (y * 80)) / mDragController.dpY) * height);
                        }
                        else
                        {
                            breite = (int) (((65.0 + (x * 100)) / mDragController.dpX) * width);
                            hoehe = (int) (((97.0 + (y * 100)) / mDragController.dpY) * height);
                        }
                        if (j == 1) {
                            breite = (int) (breite + 16.5 * width /  mDragController.dpX);
                            hoehe = (int) (hoehe - 10 * height /  mDragController.dpY);
                        }

                        textView_parameter.leftMargin = breite;
                        textView_parameter.topMargin = hoehe;
                        NumberView.add(zaehler, textView);
                        ge.addView(NumberView.get(zaehler), textView_parameter);

                        zaehler++;
                    }
                    if (j < 14 && j > 9) {
                        dimKraft++;
                    }
                }
            }
        }
        number = nummer;

        int dim = jointlist2.size();
        int dim2 = elementlist2.size();
        statBest = 2 * dim - dim2 + dimKraft;
    }

    // Erstelle Matrix A für LGS A*x=b
    private void setMatrizen() {

        int dim = jointlist2.size();
        int dim2 = elementlist2.size();
        AMat = new double[2 * dim][dim2];
        FVek = new double[2 * dim][1];


        int j;
        int i;
        for (i = 0; i < dim2; i++) {
            int x = (int) Integer.parseInt(elementlist2.get(i));
            int stabPos = x % 88;
            int stabArt = (int) (x / 88);
            for (j = 0; j < dim; j++) {
                   int jointPos = Integer.parseInt(jointlist2.get(j));
                if (jointPos == stabPos) {
                    if (stabArt == 6) {
                        AMat[2 * j][i] = 0.7071;
                        AMat[2 * j + 1][i] = 0.7071;
                    } else if (stabArt == 7) {
                        AMat[2 * j][i] = -0.7071;
                        AMat[2 * j + 1][i] = 0.7071;
                    } else if (stabArt == 8) {
                        AMat[2 * j][i] = 1;
                    } else if (stabArt == 9) {
                        AMat[2 * j + 1][i] = 1;

                    } else if (stabArt < 2) {
                        AMat[2 * j][i] = 1;
                    } else if (stabArt > 13) {
                        AMat[2 * j + 1][i] = 1;
                    } else if (stabArt == 2) {
                        AMat[2 * j + 1][i] = 1;
                    } else if (stabArt == 3) {
                        AMat[2 * j][i] = -1;
                    } else if (stabArt == 4) {
                        AMat[2 * j][i] = -0.7071;
                        AMat[2 * j + 1][i] = 0.7071;
                    } else if (stabArt == 5) {
                        AMat[2 * j][i] = 0.7071;
                        AMat[2 * j + 1][i] = 0.7071;
                    } else if (stabArt == 10) {
                        FVek[2 * j + 1][0] = 1;
                    } else if (stabArt == 11) {
                        FVek[2 * j + 1][0] = -1;
                    } else if (stabArt == 12) {
                        FVek[2 * j][0] = -1;
                    } else if (stabArt == 13) {
                        FVek[2 * j][0] = 1;
                    } else {
                    }
                } else if (jointPos == stabPos + 1 && stabArt == 8) {
                    AMat[2 * j][i] = -1;
                } else if (jointPos == stabPos - 10 && stabArt == 6) {
                    AMat[2 * j][i] = -0.7071;
                    AMat[2 * j + 1][i] = -0.7071;
                } else if (jointPos == stabPos - 11 && stabArt == 9) {
                    AMat[2 * j + 1][i] = -1;
                } else if (jointPos == stabPos - 12 && stabArt == 7) {
                    AMat[2 * j][i] = 0.7071;
                    AMat[2 * j + 1][i] = -0.7071;
                } else {
                }
            }
        }
    }

    // Lösen des LGS A*x=b mit Gauß-Algorithmus mit Spaltenpivotsuche
    private void SolveLGS() {

        int i;
        int j;
        int k;
        double b;
        int dim = AMat.length;
        XVek = new double[dim][1];
        for (j = 0; j < dim; j++) {
            for (i = j + 1; i < dim; i++) {
                if (Math.abs(AMat[i][j]) > Math.abs(AMat[j][j])) {
                    for (k = j; k < dim; k++) {
                        b = AMat[j][k];
                        AMat[j][k] = AMat[i][k];
                        AMat[i][k] = b;
                    }
                    b = FVek[j][0];
                    FVek[j][0] = FVek[i][0];
                    FVek[i][0] = b;
                }
            }

            for (i = j + 1; i < dim; i++) {
                b = AMat[i][j] / AMat[j][j];
                for (k = j; k < dim; k++) {
                    AMat[i][k] = AMat[i][k] - b * AMat[j][k];
                }

                FVek[i][0] = FVek[i][0] - b * FVek[j][0];
            }

        }


        XVek[dim - 1][0] = FVek[dim - 1][0] / AMat[dim - 1][dim - 1];
        for (j = dim - 2; j > -1; j--) {
            b = FVek[j][0];
            for (i = j + 1; i < dim; i++) {
                b = b - XVek[i][0] * AMat[j][i];
            }
            XVek[j][0] = b / AMat[j][j];
        }
        if (AMat[dim-1][dim-1]==0){
            stat_best = false;
        }
    }


    // Für Toasts
    private void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    // Löschen
    public void DeleteJoints() {
        int i;
        for (i = 0; i < jointZaehler; i++) {
            JointView.get(i).setImageResource(0);
            JointView.get(i).setVisibility(View.INVISIBLE);
        }
        for (i = 0; i < number; i++) {
            NumberView.get(i).setVisibility(View.INVISIBLE);
        }
    }

    // Nullstäbe ermitteln und in Array speichern
    private void FindNullstaebe() {
        int i;
        int j;
        int ii;
        int ii2;
        int ii3;
        lagerreaktionen = 0;
        int stabzaehler;
        int dim = AMat.length;
        Nullstaebe = new ArrayList<>();
        for (i = 0; i < dim; i++) {
            Nullstaebe.add(i, "");
            if (Integer.parseInt(elementlist2.get(i)) < 6 * 88 || Integer.parseInt(elementlist2.get(i)) > 14 * 88 - 1) {
                lagerreaktionen++;
            }
        }
        for (i = 0; i < dim; i++) {
            ii = -1;
            stabzaehler = 0;
            for (j = 0; j < dim; j++) {
                if (AMat[i][j] != 0) {
                    ii = j;
                    stabzaehler++;
                }
            }
            if (stabzaehler == 1 && FVek[i][0] == 0 && ii > lagerreaktionen - 1) {
                Nullstaebe.set(ii, "y");
            }
        }
        for (i = 0; i < dim; i = i + 2) {
            ii = -1;
            ii2 = -1;
            stabzaehler = 0;
            for (j = 0; j < dim; j++) {
                if (AMat[i][j] != 0) {
                    if (stabzaehler == 0) {
                        ii = j;
                    }
                    if (stabzaehler == 1) {
                        ii2 = j;
                    }
                    stabzaehler++;
                }
            }
            if (FVek[i][0] != 0) {
                stabzaehler++;
            }
            if (stabzaehler < 3) {
                stabzaehler = 0;
                for (j = 0; j < dim; j++) {
                    if (AMat[i + 1][j] != 0) {
                        if (j != ii && j != ii2) {
                            stabzaehler++;
                        }
                    }
                }
                if (stabzaehler == 0 && FVek[i + 1][0] == 0 && ii > lagerreaktionen - 1) {
                    Nullstaebe.set(ii, "y");
                }
                if (stabzaehler == 0 && FVek[i + 1][0] == 0 && ii2 > lagerreaktionen - 1) {
                    Nullstaebe.set(ii2, "y");
                }
            }
            ii = -1;
            ii2 = -1;
            stabzaehler = 0;
            for (j = 0; j < dim; j++) {
                if (AMat[i + 1][j] != 0) {
                    if (stabzaehler == 0) {
                        ii = j;
                    }
                    if (stabzaehler == 1) {
                        ii2 = j;
                    }
                    stabzaehler++;
                }
            }
            if (FVek[i + 1][0] != 0) {
                stabzaehler++;
            }
            if (stabzaehler < 3) {
                stabzaehler = 0;
                for (j = 0; j < dim; j++) {
                    if (AMat[i][j] != 0) {
                        if (j != ii && j != ii2) {
                            stabzaehler++;
                        }
                    }
                }
                if (stabzaehler == 0 && FVek[i][0] == 0 && ii > lagerreaktionen - 1) {
                    Nullstaebe.set(ii, "y");
                }
                if (stabzaehler == 0 && FVek[i][0] == 0 && ii2 > lagerreaktionen - 1) {
                    Nullstaebe.set(ii2, "y");
                }
            }
        }

        for (i = 0; i < dim; i = i + 2) {
           stabzaehler = 0;
            ii3 = -1;
           if (FVek[i][0]==0 && FVek[i+1][0]==0){
               ii = -1;
               ii2 = -1;
               for (j=0; j<dim; j++) {
                   if (AMat[i][j] != 0 && AMat[i + 1][j] != 0 && stabzaehler == 0) {
                       ii = j;
                       stabzaehler++;
                   }
               }
               for (j=0; j<dim; j++) {
                   if (ii!= -1 && j != ii && stabzaehler == 1) {
                       if (AMat[i][j] == AMat[i][ii] && AMat[i + 1][j] == AMat[i + 1][ii] ||
                               AMat[i][j] == -AMat[i][ii] && AMat[i + 1][j] == -AMat[i + 1][ii]) {
                           ii2 = j;
                           stabzaehler++;
                       }
                   }
               }
               for (j=0; j<dim; j++) {
                   if (ii2 != -1 && stabzaehler > 1){
                       if(AMat[i][j] != 0 && j!= ii  && j!= ii2){
                           stabzaehler++;
                           ii3 = j;
                       }
                   }
               }
               if (stabzaehler == 3){
                   Nullstaebe.set(ii3, "y");
               }
               }
           }
        }


    //Öffnen von Nullstabquiz
    private void NullstabQuiz() {

        Stabliste = new ArrayList<>();
        Vergleich = new ArrayList<>();
        NullstabErgebnis = new ArrayList<>();
        Integer lauf;
        float width = mDraglayer.getWidth();

        for (lauf = lagerreaktionen+1; lauf < AMat.length+1; lauf++) {
            Stabliste.add(lauf - lagerreaktionen-1, "S" + lauf.toString());
            Vergleich.add(lauf - lagerreaktionen-1,"");
        }

        CharSequence[] StabSeq = Stabliste.toArray(new CharSequence[Stabliste.size()]);

        ContextThemeWrapper cw = new ContextThemeWrapper(this, R.style.AlertDialogTheme );

        AlertDialog.Builder builder = new AlertDialog.Builder(cw);

        TextView title =  new TextView(context);
        title.setText("Nullstäbe?");
        title.setTextColor(Color.rgb(38,181,234));
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);

        builder
                .setCustomTitle(title)
                .setMultiChoiceItems(StabSeq, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                        if (isChecked) {
                            Vergleich.add(which, "y");

                        } else {
                            Vergleich.add(which, "");
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        NullstabCheck();

                    }
                })
                .setNegativeButton("X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                });

        AlertDialog alertDialog_2 = builder.create();

        WindowManager.LayoutParams np = new WindowManager.LayoutParams();
        np.copyFrom(alertDialog_2.getWindow().getAttributes());
        np.width = (int)((150.0*width)/600.0);
        np.height = WindowManager.LayoutParams.WRAP_CONTENT;
        np.gravity = Gravity.RIGHT;

        alertDialog_2.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog_2.show();
        alertDialog_2.getWindow().setAttributes(np);
        alertDialog_2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    // Überprüfung des Quiz
    private void NullstabCheck(){
        String Istgleich;
        String richtig;
        float width = mDraglayer.getWidth();

        for (Integer i = lagerreaktionen+1; i < AMat.length+1; i++) {
            if (Vergleich.get(i-lagerreaktionen-1) == "y") {
                Istgleich = " = ";

            } else {
                Istgleich = " /= ";
            }
            if (Vergleich.get(i-lagerreaktionen-1)==Nullstaebe.get(i-1)){
                richtig = " richtig";
            } else {
                richtig = " falsch";
            }
            NullstabErgebnis.add(i-lagerreaktionen-1, "S" + i.toString()+ Istgleich + "0" + richtig);

        }
        CharSequence[] NullstabErgebnisse = NullstabErgebnis.toArray(new CharSequence[NullstabErgebnis.size()]);

        ContextThemeWrapper cw = new ContextThemeWrapper(this, R.style.AlertDialogTheme );
        TextView title =  new TextView(context);
        title.setText("Ergebnis");
        title.setTextColor(Color.rgb(38,181,234));
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);


        AlertDialog.Builder builder_antwort = new AlertDialog.Builder(cw);
        builder_antwort.setCustomTitle(title)
                .setItems(NullstabErgebnisse, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        AlertDialog alertDialog_3 = builder_antwort.create();
        WindowManager.LayoutParams mp = new WindowManager.LayoutParams();
        mp.copyFrom(alertDialog_3.getWindow().getAttributes());
        mp.width = (int)((200.0*width)/600.0);
        mp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mp.gravity = Gravity.RIGHT ;


        alertDialog_3.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog_3.show();
        alertDialog_3.getWindow().setAttributes(mp);
        alertDialog_3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }


    // Anzeigen der Lösung
    private void Loesung(){
        double z;
        Integer i;
        String S_oder_L="";
        float width = mDraglayer.getWidth();

        loesungsArray = new ArrayList<>();
        for(i=1;i<AMat.length+1;i++){
            if (i<lagerreaktionen+1){
                S_oder_L = "L";
            }
            else {
                S_oder_L = "S";
            }
            z = XVek[i-1][0]*100;
            z = Math.round(z);
            z = z/100;
            if (z<0.01 && z>-0.01){
                z=0;
            }
            loesungsArray.add(i-1,S_oder_L + i.toString() + " = " + String.valueOf(z) + " F");
        }

        CharSequence[] loesSeq = loesungsArray.toArray(new CharSequence[loesungsArray.size()]);

        ContextThemeWrapper cw = new ContextThemeWrapper(this, R.style.AlertDialogTheme );

        TextView title =  new TextView(context);
        title.setText("Lösung");
        title.setTextColor(Color.rgb(38,181,234));
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);

        AlertDialog.Builder builder_antwort = new AlertDialog.Builder(cw);
        builder_antwort
                .setCustomTitle(title)
                .setItems(loesSeq, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });

        AlertDialog alertDialog_4 = builder_antwort.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog_4.getWindow().getAttributes());
        lp.width = (int)((150.0*width)/600.0);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.RIGHT;

        alertDialog_4.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        alertDialog_4.show();
        alertDialog_4.getWindow().setAttributes(lp);
        alertDialog_4.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }


}