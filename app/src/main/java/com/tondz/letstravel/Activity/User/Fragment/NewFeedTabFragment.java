package com.tondz.letstravel.Activity.User.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tondz.letstravel.Activity.User.ModuleAdapter.NewFeedListAdapter;
import com.tondz.letstravel.R;

import java.util.ArrayList;

public class NewFeedTabFragment extends Fragment {
    ListView lv_newfeeds;
    ViewGroup viewGroup;
    NewFeedListAdapter newFeedListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.new_feeds_tab_fragment,container,false);
        initView();
        setLv_newfeeds();
        return viewGroup;
    }
    private void initView(){
        lv_newfeeds = viewGroup.findViewById(R.id.listview_newfeeds);
    }

    public void setLv_newfeeds() {
//        ArrayList<NewFeeds>newFeedsArrayList = new ArrayList<>();
//        newFeedsArrayList.add(new NewFeeds(R.drawable.phocohoian,"Phố cổ Hội An, điểm đến hấp dẫn","Thành phố Hội An nằm bên bờ bắc hạ lưu sông Thu Bồn. Hội An là một đô thị cổ của Việt Nam, cách Hà Nội 795 km về phía Nam, cách Thành phố Hồ Chí Minh 940 km, cách Huế 122 km, cách thành phố Đà Nẵng 30 km về phía đông nam, có vị trí địa lý:\n" +
//                "\n" +
//                "Phía đông giáp biển Đông\n" +
//                "Phía tây giáp thị xã Điện Bàn\n" +
//                "Phía nam giáp huyện Duy Xuyên\n" +
//                "Phía bắc giáp thị xã Điện Bàn và biển Đông."));
//        newFeedsArrayList.add(new NewFeeds(R.drawable.vinhhalong,"Vịnh Hạ Long","Vịnh Hạ Long – được Unesco nhiều lần công nhận là di sản thiên nhiên của thế giới với hàng nghìn hòn đảo được làm nên bởi tạo hoá kỳ vĩ và sống động. Vịnh Hạ Long có phong cảnh tuyệt đẹp nên nơi đây là một điểm du lịch rất hấp dẫn với du khách trong nước và quốc tế.\n" +
//                "\n" +
//                "Vịnh Hạ Long là một di sản độc đáo bởi địa danh này chứa đựng những dấu tích quan trọng trong quá trình hình thành và phát triển lịch sử trái đất, là cái nôi cư trú của người Việt cổ, đồng thời là tác phẩm nghệ thuật tạo hình vĩ đại của thiên nhiên với sự hiện diện của hàng nghìn đảo đá muôn hình vạn trạng, với nhiều hang động kỳ thú quần tụ thành một thế giới vừa sinh động vừa huyền bí. Bên cạnh đó, vịnh Hạ Long còn là nơi tập trung đa dạng sinh học cao với những hệ sinh thái điển hình cùng với hàng nghìn loài động thực vật vô cùng phong phú, đa dạng. Nơi đây còn gắn liền với những giá trị văn hóa – lịch sử hào hùng của dân tộc."));
//        newFeedListAdapter = new NewFeedListAdapter(getContext(),newFeedsArrayList);
//        lv_newfeeds.setAdapter(newFeedListAdapter);
    }
}
