package com.example.huge.fzugang.CarpoolBlock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.huge.fzugang.CarpoolBlock.ToJSON.CarpoolReleasePost;
import com.example.huge.fzugang.R;
import com.example.huge.fzugang.Utils.ActivityCollector;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarpoolBasicAddActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private CarpoolReleasePost carpoolReleasePost;
    private String meetPlace;
    private String destination;
    private String price;
    private String mDate;
    private String mTime;
    private int numOfPeople = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_basic_add);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);

        QMUIGroupListView carpoolReleaseActivityGroupListView
                = (QMUIGroupListView) findViewById(R.id.carpool_basic_add_group_list_view);

        final QMUICommonListItemView meetingPlaceItem = carpoolReleaseActivityGroupListView
                .createItemView("见面地点");
        meetingPlaceItem.setDetailText("点击输入见面地点");
        meetingPlaceItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        final QMUICommonListItemView destinationItem = carpoolReleaseActivityGroupListView
                .createItemView("目的地");
        destinationItem.setDetailText("点击输入目的地");
        destinationItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView dateItem = carpoolReleaseActivityGroupListView
                .createItemView("出发日期");
        dateItem.setDetailText("选择日期");
        dateItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView timeItem = carpoolReleaseActivityGroupListView
                .createItemView("见面时间");
        timeItem.setDetailText("选择时间");
        timeItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        final QMUICommonListItemView priceItem = carpoolReleaseActivityGroupListView
                .createItemView("价格");
        priceItem.setDetailText("点击输入价格");
        priceItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        final QMUICommonListItemView numOfPeopleItem = carpoolReleaseActivityGroupListView
                .createItemView("拼车人数");
        numOfPeopleItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView confirm = carpoolReleaseActivityGroupListView
                .createItemView("确认");
        confirm.setDetailText("继续编辑详细内容");
        confirm.setAccessoryType(QMUICommonListItemView.VERTICAL);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QMUICommonListItemView v = (QMUICommonListItemView) view;
                switch (v.getText().toString()) {
                    case "见面地点":
                        showQMUIEditTextDiaLog(meetingPlaceItem, "见面地点", InputType.TYPE_TEXT_VARIATION_PHONETIC);
                        break;
                    case "目的地":
                        showQMUIEditTextDiaLog(destinationItem, "目的地", InputType.TYPE_TEXT_VARIATION_PHONETIC);
                        break;
                    case "价格":
                        showQMUIEditTextDiaLog(priceItem, "价格", InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    case "拼车人数":
                        showQMUICheckableDialog(numOfPeopleItem);
                        break;
                    case "出发日期":
                        showTimePicker(v, new boolean[]{true, true, true, false, false, false});
                        break;
                    case "见面时间":
                        showTimePicker(v, new boolean[]{false, false, false, true, true, false});
                        break;
                    case "确认":
                        if (meetPlace != null && destination != null && price != null && mDate != null && mTime != null
                                && numOfPeople != 0) {
                            Intent intent = new Intent(CarpoolBasicAddActivity.this, CarpoolContentAddActivity.class);
                            intent.putExtra("meetPlace", meetPlace);
                            intent.putExtra("destination", destination);
                            intent.putExtra("price", price);
                            intent.putExtra("date", mDate);
                            intent.putExtra("time", mTime);
                            intent.putExtra("numOfPeople", numOfPeople);
                            CarpoolBasicAddActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(CarpoolBasicAddActivity.this, "请检查输入是否完整,并重试", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        break;
                    default:
                        break;
                }
            }

        };
        QMUIGroupListView.newSection(CarpoolBasicAddActivity.this)
                .setTitle("拼车基本信息")
                .setDescription("请确认拼车信息")
                .addItemView(meetingPlaceItem, onClickListener)
                .addItemView(destinationItem, onClickListener)
                .addItemView(dateItem, onClickListener)
                .addItemView(timeItem, onClickListener)
                .addItemView(priceItem, onClickListener)
                .addItemView(numOfPeopleItem, onClickListener)
                .addTo(carpoolReleaseActivityGroupListView);

        QMUIGroupListView.newSection(CarpoolBasicAddActivity.this)
                .addItemView(confirm, onClickListener)
                .addTo(carpoolReleaseActivityGroupListView);
    }

    private void showTimePicker(final QMUICommonListItemView listItemView, final boolean[] pickerType) {
        TimePickerView timePickerView = new TimePickerBuilder(CarpoolBasicAddActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat;
                String result;
                String postResult;
                if (pickerType[0] == true) {
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    result = SimpleDateFormat.getDateInstance().format(date);
                    postResult = dateFormat.format(date);
                    mDate = postResult;

                } else {
                    dateFormat = new SimpleDateFormat("HH:mm");
                    postResult = dateFormat.format(date);
                    result = postResult;
                    mTime = postResult;
                }
                listItemView.setDetailText(result);
                Log.d("test", "onTimeSelect: " + postResult);
            }
        }).isDialog(true)
                .setType(pickerType).build();
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }

    private void showQMUIEditTextDiaLog(final QMUICommonListItemView view, final String s, int typeClassText) {
        final QMUIDialog.EditTextDialogBuilder builder
                = new QMUIDialog.EditTextDialogBuilder(CarpoolBasicAddActivity.this);

        builder.setTitle(s)
                .setPlaceholder("在此输入" + s)
                .setInputType(typeClassText)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String text = builder.getEditText().getText().toString();
                        if (text != null && text.length() > 0) {
                            switch (view.getText().toString()) {
                                case "见面地点":
                                    Log.d("test", "onClick: " + view.getText().toString());
                                    setDetail(view, text);
                                    meetPlace = text;
                                    dialog.dismiss();
                                    break;
                                case "目的地":
                                    Log.d("test", "onClick: " + view.getText().toString());
                                    setDetail(view, text);
                                    destination = text;
                                    dialog.dismiss();
                                    break;
                                case "价格":
                                    Log.d("test", "onClick: " + view.getText().toString());
                                    if (isDoubleOrFloat(text)) {
                                        setDetail(view, text);
                                        price = text;
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(CarpoolBasicAddActivity.this, "请输入纯数字", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            Toast.makeText(CarpoolBasicAddActivity.this, "请输入" + s, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    private boolean isDoubleOrFloat(String text) {
                        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
                        return pattern.matcher(text).matches();
                    }

                }).show();
    }

    private void showQMUICheckableDialog(final QMUICommonListItemView numOfPeopleItem) {
        final String[] items = new String[]{"1", "2", "3"};
        final int checkIndex = 1;
        new QMUIDialog.CheckableDialogBuilder(CarpoolBasicAddActivity.this)
                .setCheckedIndex(checkIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        numOfPeopleItem.setDetailText(items[i] + "人");
                        setNumOfPeople(Integer.parseInt(items[i]));
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    private void setDetail(QMUICommonListItemView view, String detail) {
        view.setDetailText(detail);
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
