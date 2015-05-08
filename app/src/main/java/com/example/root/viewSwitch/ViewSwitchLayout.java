package com.example.root.viewSwitch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.root.main.alarmandmusic.R;
import com.example.root.scroll.Item;
import com.example.root.scroll.ScrollLayout2;
import com.example.root.scroll.ScrollUtils;

import java.util.ArrayList;

import static com.example.root.scroll.ScrollBaseAdapter.NUM_OF_ITEM;

/**
 * Created by zhanglei on 15-5-3.
 * Email: zhangleicoding@163.com
 */
public class ViewSwitchLayout {
    private Context context;
    private RelativeLayout fragActivity;
    private LayoutInflater mInflater;
    private RelativeLayout scrollRes;

    private RelativeLayout.LayoutParams scrollLayoutParams;

    private static ArrayList<Item> itemsData = ScrollUtils.getItems(NUM_OF_ITEM);

    public ViewSwitchLayout(Context context, RelativeLayout fragActivity) {
        this.context = context;
        this.fragActivity = fragActivity;

        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(context);
        // scrollRes = (RelativeLayout)mInflater.inflate(R.layout.scroll_list, null);
        ViewPager viewPager = (ViewPager) ((FragmentActivity)context).findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter());

        scrollLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 5;






        public SampleFragmentPagerAdapter() {
            super(((FragmentActivity)context).getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int i) {
            return PageFragment.create(i + 1);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "" + (position + 1);
        }
    }


    public static class PageFragment extends Fragment {
        public static final String ARG_PAGE = "ARG_PAGE";

        private int mPage;

        private ScrollLayout2 scrollLayout;

        public static PageFragment create(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            PageFragment fragment = new PageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // View view = inflater.inflate(R.layout.scroll_list, container, false);
            RelativeLayout scrollRelative = (RelativeLayout)inflater.inflate(R.layout.scroll_list, container, false);
            scrollLayout = new ScrollLayout2(scrollRelative, itemsData);
            return scrollLayout.getTimePicker();
        }


    }


}
