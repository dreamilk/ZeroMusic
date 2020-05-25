package com.shanghq.zeromusic.bean;

import java.util.List;

public class WeatherData {


    /**
     * status : ok
     * basic : {"city":"武汉","cnty":"中国","id":"101200101","lat":"30.58435440","lon":"114.29856873","parent_city":"武汉","prov":"湖北","updateTime":"16:40"}
     * now : {"code":"01","hum":"67","pcpn":"0.0","pres":"1023","tmp":"5","txt":"多云","wind_dir_txt":"东北风","wind_sc":"3"}
     * aqi : {"aqi":"55","txt":"良"}
     * forecast : {"date":"202001081100","forecast":[{"tmp_max":"6","tmp_min":"0","tqw_code_d":"07","tqw_code_n":"08","wind_dir_d_txt":"东北风","wind_dir_n_txt":"东北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"3-4级"},{"tmp_max":"5","tmp_min":"1","tqw_code_d":"07","tqw_code_n":"07","wind_dir_d_txt":"东北风","wind_dir_n_txt":"北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"微风"},{"tmp_max":"4","tmp_min":"2","tqw_code_d":"08","tqw_code_n":"07","wind_dir_d_txt":"北风","wind_dir_n_txt":"北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"3-4级"},{"tmp_max":"7","tmp_min":"0","tqw_code_d":"07","tqw_code_n":"01","wind_dir_d_txt":"北风","wind_dir_n_txt":"北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"微风"},{"tmp_max":"7","tmp_min":"0","tqw_code_d":"02","tqw_code_n":"00","wind_dir_d_txt":"东风","wind_dir_n_txt":"东风","wind_sc_d_txt":"微风","wind_sc_n_txt":"微风"},{"tmp_max":"8","tmp_min":"1","tqw_code_d":"00","tqw_code_n":"02","wind_dir_d_txt":"东风","wind_dir_n_txt":"北风","wind_sc_d_txt":"微风","wind_sc_n_txt":"微风"},{"tmp_max":"9","tmp_min":"0","tqw_code_d":"07","tqw_code_n":"07","wind_dir_d_txt":"北风","wind_dir_n_txt":"无持续风向","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"微风"}]}
     * lifestyleForecast : {"lifestyle":[{"comf":{"brf":"较不舒适"},"cw":{"brf":"不宜"},"drsg":{"brf":"冷"},"fishing":{"brf":"不宜"},"flu":{"brf":"极易发"},"sport":{"brf":"较不宜"},"trav":{"brf":"一般"},"uv":{"brf":"最弱"}}],"updateTime":"202001081100"}
     * hourly : [{"time":"20200108150000","tmp":"6","tqw_code":"07"},{"time":"20200108160000","tmp":"5","tqw_code":"07"},{"time":"20200108170000","tmp":"5","tqw_code":"07"},{"time":"20200108180000","tmp":"5","tqw_code":"07"},{"time":"20200108190000","tmp":"5","tqw_code":"07"},{"time":"20200108200000","tmp":"3","tqw_code":"07"},{"time":"20200108210000","tmp":"3","tqw_code":"07"},{"time":"20200108220000","tmp":"3","tqw_code":"07"},{"time":"20200108230000","tmp":"3","tqw_code":"07"},{"time":"20200109000000","tmp":"1","tqw_code":"06"},{"time":"20200109010000","tmp":"0","tqw_code":"06"},{"time":"20200109020000","tmp":"0","tqw_code":"06"},{"time":"20200109030000","tmp":"0","tqw_code":"06"},{"time":"20200109040000","tmp":"0","tqw_code":"06"},{"time":"20200109050000","tmp":"0","tqw_code":"06"},{"time":"20200109060000","tmp":"0","tqw_code":"06"},{"time":"20200109070000","tmp":"0","tqw_code":"06"},{"time":"20200109080000","tmp":"0","tqw_code":"06"},{"time":"20200109090000","tmp":"1","tqw_code":"07"},{"time":"20200109100000","tmp":"1","tqw_code":"07"},{"time":"20200109110000","tmp":"4","tqw_code":"07"},{"time":"20200109120000","tmp":"4","tqw_code":"07"},{"time":"20200109130000","tmp":"4","tqw_code":"07"},{"time":"20200109140000","tmp":"5","tqw_code":"07"}]
     * history : {"code_d":"07","code_n":"02","date":"20200107","tmp_max":"7","tmp_min":"-1","txt_d":"小雨","txt_n":"阴"}
     */

    private String status;
    private BasicBean basic;
    private NowBean now;
    private AqiBean aqi;
    private ForecastBeanX forecast;
    private LifestyleForecastBean lifestyleForecast;
    private HistoryBean history;
    private List<HourlyBean> hourly;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public ForecastBeanX getForecast() {
        return forecast;
    }

    public void setForecast(ForecastBeanX forecast) {
        this.forecast = forecast;
    }

    public LifestyleForecastBean getLifestyleForecast() {
        return lifestyleForecast;
    }

    public void setLifestyleForecast(LifestyleForecastBean lifestyleForecast) {
        this.lifestyleForecast = lifestyleForecast;
    }

    public HistoryBean getHistory() {
        return history;
    }

    public void setHistory(HistoryBean history) {
        this.history = history;
    }

    public List<HourlyBean> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyBean> hourly) {
        this.hourly = hourly;
    }

    public static class BasicBean {
        /**
         * city : 武汉
         * cnty : 中国
         * id : 101200101
         * lat : 30.58435440
         * lon : 114.29856873
         * parent_city : 武汉
         * prov : 湖北
         * updateTime : 16:40
         */

        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String parent_city;
        private String prov;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class NowBean {
        /**
         * code : 01
         * hum : 67
         * pcpn : 0.0
         * pres : 1023
         * tmp : 5
         * txt : 多云
         * wind_dir_txt : 东北风
         * wind_sc : 3
         */

        private String code;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String txt;
        private String wind_dir_txt;
        private String wind_sc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getWind_dir_txt() {
            return wind_dir_txt;
        }

        public void setWind_dir_txt(String wind_dir_txt) {
            this.wind_dir_txt = wind_dir_txt;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }
    }

    public static class AqiBean {
        /**
         * aqi : 55
         * txt : 良
         */

        private String aqi;
        private String txt;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public static class ForecastBeanX {
        /**
         * date : 202001081100
         * forecast : [{"tmp_max":"6","tmp_min":"0","tqw_code_d":"07","tqw_code_n":"08","wind_dir_d_txt":"东北风","wind_dir_n_txt":"东北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"3-4级"},{"tmp_max":"5","tmp_min":"1","tqw_code_d":"07","tqw_code_n":"07","wind_dir_d_txt":"东北风","wind_dir_n_txt":"北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"微风"},{"tmp_max":"4","tmp_min":"2","tqw_code_d":"08","tqw_code_n":"07","wind_dir_d_txt":"北风","wind_dir_n_txt":"北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"3-4级"},{"tmp_max":"7","tmp_min":"0","tqw_code_d":"07","tqw_code_n":"01","wind_dir_d_txt":"北风","wind_dir_n_txt":"北风","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"微风"},{"tmp_max":"7","tmp_min":"0","tqw_code_d":"02","tqw_code_n":"00","wind_dir_d_txt":"东风","wind_dir_n_txt":"东风","wind_sc_d_txt":"微风","wind_sc_n_txt":"微风"},{"tmp_max":"8","tmp_min":"1","tqw_code_d":"00","tqw_code_n":"02","wind_dir_d_txt":"东风","wind_dir_n_txt":"北风","wind_sc_d_txt":"微风","wind_sc_n_txt":"微风"},{"tmp_max":"9","tmp_min":"0","tqw_code_d":"07","tqw_code_n":"07","wind_dir_d_txt":"北风","wind_dir_n_txt":"无持续风向","wind_sc_d_txt":"3-4级","wind_sc_n_txt":"微风"}]
         */

        private String date;
        private List<ForecastBean> forecast;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class ForecastBean {
            /**
             * tmp_max : 6
             * tmp_min : 0
             * tqw_code_d : 07
             * tqw_code_n : 08
             * wind_dir_d_txt : 东北风
             * wind_dir_n_txt : 东北风
             * wind_sc_d_txt : 3-4级
             * wind_sc_n_txt : 3-4级
             */

            private String tmp_max;
            private String tmp_min;
            private String tqw_code_d;
            private String tqw_code_n;
            private String wind_dir_d_txt;
            private String wind_dir_n_txt;
            private String wind_sc_d_txt;
            private String wind_sc_n_txt;

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getTqw_code_d() {
                return tqw_code_d;
            }

            public void setTqw_code_d(String tqw_code_d) {
                this.tqw_code_d = tqw_code_d;
            }

            public String getTqw_code_n() {
                return tqw_code_n;
            }

            public void setTqw_code_n(String tqw_code_n) {
                this.tqw_code_n = tqw_code_n;
            }

            public String getWind_dir_d_txt() {
                return wind_dir_d_txt;
            }

            public void setWind_dir_d_txt(String wind_dir_d_txt) {
                this.wind_dir_d_txt = wind_dir_d_txt;
            }

            public String getWind_dir_n_txt() {
                return wind_dir_n_txt;
            }

            public void setWind_dir_n_txt(String wind_dir_n_txt) {
                this.wind_dir_n_txt = wind_dir_n_txt;
            }

            public String getWind_sc_d_txt() {
                return wind_sc_d_txt;
            }

            public void setWind_sc_d_txt(String wind_sc_d_txt) {
                this.wind_sc_d_txt = wind_sc_d_txt;
            }

            public String getWind_sc_n_txt() {
                return wind_sc_n_txt;
            }

            public void setWind_sc_n_txt(String wind_sc_n_txt) {
                this.wind_sc_n_txt = wind_sc_n_txt;
            }
        }
    }

    public static class LifestyleForecastBean {
        /**
         * lifestyle : [{"comf":{"brf":"较不舒适"},"cw":{"brf":"不宜"},"drsg":{"brf":"冷"},"fishing":{"brf":"不宜"},"flu":{"brf":"极易发"},"sport":{"brf":"较不宜"},"trav":{"brf":"一般"},"uv":{"brf":"最弱"}}]
         * updateTime : 202001081100
         */

        private String updateTime;
        private List<LifestyleBean> lifestyle;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class LifestyleBean {
            /**
             * comf : {"brf":"较不舒适"}
             * cw : {"brf":"不宜"}
             * drsg : {"brf":"冷"}
             * fishing : {"brf":"不宜"}
             * flu : {"brf":"极易发"}
             * sport : {"brf":"较不宜"}
             * trav : {"brf":"一般"}
             * uv : {"brf":"最弱"}
             */

            private ComfBean comf;
            private CwBean cw;
            private DrsgBean drsg;
            private FishingBean fishing;
            private FluBean flu;
            private SportBean sport;
            private TravBean trav;
            private UvBean uv;

            public ComfBean getComf() {
                return comf;
            }

            public void setComf(ComfBean comf) {
                this.comf = comf;
            }

            public CwBean getCw() {
                return cw;
            }

            public void setCw(CwBean cw) {
                this.cw = cw;
            }

            public DrsgBean getDrsg() {
                return drsg;
            }

            public void setDrsg(DrsgBean drsg) {
                this.drsg = drsg;
            }

            public FishingBean getFishing() {
                return fishing;
            }

            public void setFishing(FishingBean fishing) {
                this.fishing = fishing;
            }

            public FluBean getFlu() {
                return flu;
            }

            public void setFlu(FluBean flu) {
                this.flu = flu;
            }

            public SportBean getSport() {
                return sport;
            }

            public void setSport(SportBean sport) {
                this.sport = sport;
            }

            public TravBean getTrav() {
                return trav;
            }

            public void setTrav(TravBean trav) {
                this.trav = trav;
            }

            public UvBean getUv() {
                return uv;
            }

            public void setUv(UvBean uv) {
                this.uv = uv;
            }

            public static class ComfBean {
                /**
                 * brf : 较不舒适
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class CwBean {
                /**
                 * brf : 不宜
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class DrsgBean {
                /**
                 * brf : 冷
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class FishingBean {
                /**
                 * brf : 不宜
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class FluBean {
                /**
                 * brf : 极易发
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class SportBean {
                /**
                 * brf : 较不宜
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class TravBean {
                /**
                 * brf : 一般
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }

            public static class UvBean {
                /**
                 * brf : 最弱
                 */

                private String brf;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }
            }
        }
    }

    public static class HistoryBean {
        /**
         * code_d : 07
         * code_n : 02
         * date : 20200107
         * tmp_max : 7
         * tmp_min : -1
         * txt_d : 小雨
         * txt_n : 阴
         */

        private String code_d;
        private String code_n;
        private String date;
        private String tmp_max;
        private String tmp_min;
        private String txt_d;
        private String txt_n;

        public String getCode_d() {
            return code_d;
        }

        public void setCode_d(String code_d) {
            this.code_d = code_d;
        }

        public String getCode_n() {
            return code_n;
        }

        public void setCode_n(String code_n) {
            this.code_n = code_n;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTmp_max() {
            return tmp_max;
        }

        public void setTmp_max(String tmp_max) {
            this.tmp_max = tmp_max;
        }

        public String getTmp_min() {
            return tmp_min;
        }

        public void setTmp_min(String tmp_min) {
            this.tmp_min = tmp_min;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }
    }

    public static class HourlyBean {
        /**
         * time : 20200108150000
         * tmp : 6
         * tqw_code : 07
         */

        private String time;
        private String tmp;
        private String tqw_code;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getTqw_code() {
            return tqw_code;
        }

        public void setTqw_code(String tqw_code) {
            this.tqw_code = tqw_code;
        }
    }
}
