package org.xmmmm.github;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Json {



    public static Object[] getGroup(String json){
        Object[] objects=new Object[3];
        List<Group> groups=new ArrayList<>();
        Map<String,Long> groupnameToUin=new HashMap<>();
        Map<Long,Group> uinToGroup=new HashMap<>();

        JSONObject jsonObject=new JSONObject(json);
        JSONObject jo=jsonObject.getJSONObject("result");
        JSONArray jsonArray=jo.getJSONArray("gnamelist");
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject job=jsonArray.getJSONObject(i);

            Group group=new Group();
            group.setCode(job.getLong("code"));
            group.setFlag(job.getLong("flag"));
            group.setGid(job.getLong("gid"));
            group.setName(job.getString("name"));
            groups.add(group);
            if(group.getName().equals("【BK】MC服主rpg交流群"))
            {
                Logger.info("【BK】MC服主rpg交流群:true");
            }
            groupnameToUin.put(group.getName(),group.getGid());
            Logger.info(String.valueOf(groupnameToUin.get("【BK】MC服主rpg交流群")));
            uinToGroup.put(group.getGid(),group);
            Logger.info(group.toString());
        }
        objects[0]=groups;
        objects[1]=groupnameToUin;
        objects[2]=uinToGroup;

        return objects;
    }



    @Test
    public static Object[] getFriend(String json){
        Object[] objects=new Object[3];

        List<Friend> friends=new ArrayList<>();
        Map<Long,Friend> uinToFriend = new HashMap<>();
        Map<String,Long> nameToUin = new HashMap<>();

        JSONObject jsonObject=new JSONObject(json);
        JSONObject result=jsonObject.getJSONObject("result");
        JSONArray frends=result.getJSONArray("friends");

        for (int i = 0; i < frends.length(); i++) {
            JSONObject j = frends.getJSONObject(i);
            Friend friend=new Friend();
            friend.setUin(j.getLong("uin"));
            friend.setFriendFlag(j.getInt("flag"));
            friend.setCategories(j.getInt("categories"));
            friends.add(friend);
            uinToFriend.put(friend.getUin(),friend);
        }
        Friend friend=null;
        JSONArray info=result.getJSONArray("info");
        for (int i = 0; i < info.length(); i++) {
            JSONObject j = info.getJSONObject(i);
            long uin=j.getLong("uin");
            if(uinToFriend.get(uin)!=null){
                friend=uinToFriend.get(uin);
                friend.setFace(j.getInt("face"));
                friend.setInfoFlag(j.getInt("flag"));
                friend.setNick(j.getString("nick"));
            }
        }
        JSONArray marknames=result.getJSONArray("marknames");
        for (int i = 0; i < marknames.length(); i++) {
            JSONObject j = marknames.getJSONObject(i);
            long uin=j.getLong("uin");
            if(uinToFriend.get(uin)!=null){
                friend=uinToFriend.get(uin);
                friend.setMarkname(j.getString("markname"));
                friend.setType(j.getInt("type"));
            }
        }
        JSONArray vipinfo=result.getJSONArray("vipinfo");
        for (int i = 0; i < vipinfo.length(); i++) {
            JSONObject j = vipinfo.getJSONObject(i);
            long uin=j.getLong("u");
            if(uinToFriend.get(uin)!=null){
                friend=uinToFriend.get(uin);
                friend.setIsVip(j.getInt("is_vip"));
                friend.setVipLevel(j.getInt("vip_level"));
            }
        }
        for (Friend friend1:
             friends) {
            Logger.info(friend1.toString());
        }
        objects[0]=friends;
        objects[1]=uinToFriend;
        for (Friend f:
             friends) {
            if(f.getMarkname()!=null){
                nameToUin.put(f.getMarkname(),f.getUin());
                Logger.info(f.getMarkname());
            }else {
                nameToUin.put(f.getNick(),f.getUin());
                Logger.info(f.getNick());
            }
        }
        objects[2]=nameToUin;
        return objects;
    }

}
