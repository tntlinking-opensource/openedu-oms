create or replace function get_constellation_by_card_code(card_code in varchar2)
  return varchar2 is
  birthday      varchar2(50);
  constellation varchar2(50);
begin
  select substr(card_code, 11, 4) into birthday from dual;
  select case
           when '0321' <= birthday and birthday <= '0420' then
            '白羊座'
           when '0421' <= birthday and birthday <= '0521' then
            '金牛座'
           when '0522' <= birthday and birthday <= '0621' then
            '双子座'
           when '0622' <= birthday and birthday <= '0722' then
            '巨蟹座'
           when '0723' <= birthday and birthday <= '0822' then
            '狮子座'
           when '0823' <= birthday and birthday <= '0922' then
            '处女座'
           when '0923' <= birthday and birthday <= '1023' then
            '天秤座'
           when '1024' <= birthday and birthday <= '1122' then
            '天蝎座'
           when '1123' <= birthday and birthday <= '1221' then
            '射手座'
           when '1222' <= birthday or birthday <= '0120' then
            '摩羯座'
           when '0121' <= birthday and birthday <= '0219' then
            '水瓶座'
           when '0220' <= birthday and birthday <= '0320' then
            '双子座'
         end
    into constellation
    from dual;
  return constellation;
end get_constellation_by_card_code;
