package model.logic;

import model.data_structures.*;


import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Definicion del modelo del mundo
 */
public class Modelo{
    /**
     * Atributos del modelo del mundo
     */
    private RedBlackBST<String,Features> rbt1;
    private RedBlackBST<String,Features> rbt2;
    private RedBlackBST<String,Features> rbt3;
    private LinearProbingHashST<String,Features[]> lp;
    private SeparateChainingHashST<String,Features[]> sc;
    private MaxPQ<Features> pq1;
    private MaxPQ<Features> pq2;
    private Features[] comparableF;
    private int tamanio;
    private String mayorObj;
    private String minObj;
    private Double[] MinMax;

    public Modelo() {
        // arregloD = null;
        tamanio = 0;
        mayorObj = null;
    }

    public Modelo(List<Features> listaFeatures, int lp, int sc,int pq,Primos primos) {
        cargarComparendos(listaFeatures,lp,sc,pq,primos);
    }
    public void cargarComparendos(List<Features> listaFeatures,int lp, int sc,int pq,Primos primos) {
        try {
            long startTime = System.nanoTime();
            comparableF = new Features[listaFeatures.size()];
            for (int i = 0; i<listaFeatures.size();i++){
                comparableF[i] = listaFeatures.get(i);
             }// revisar tipo de sort
            PQS(comparableF,pq);
            RBTS(comparableF);
            getMayorOBJ();
            getMinOBJ();
            HASH(comparableF,primos,lp,sc);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double convertET = (double) elapsedTime / 1000000000;
            System.out.println("Red-Black Binary Search Tree size: \n\t N:" + this.rbt1.size());
            System.out.println("Linear Probing hash table size: \n\tM: " + this.lp.sizeM() + "\n\tN: "+this.lp.sizeN());
            System.out.println("Separate chaining hash table size: \n\tM: " + this.sc.sizeM() + "\n\tN: "+this.sc.sizeN());
            System.out.println(
                    "Datos cargados en estructuras -----// \n\tTime elapsed loading data: " + convertET + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void PQS(Features[] list, int pq){
        this.pq1 = new MaxPQ<>(pq, new Comparator<Features>(){
            @Override
            public int compare(Features f1, Features f2) {
                int p1 = 0;
                int p2 = 0;
                if(f1.getProperties().getTIPO_SERVICIO().equalsIgnoreCase("público"))
                    p1 = 3;
                else if (f1.getProperties().getTIPO_SERVICIO().equalsIgnoreCase("oficial"))
                    p1 = 2;
                else p1 = 1;
                if(f2.getProperties().getTIPO_SERVICIO().equalsIgnoreCase("público"))
                    p2 = 3;
                else if (f2.getProperties().getTIPO_SERVICIO().equalsIgnoreCase("oficial"))
                    p2 = 2;
                else p2 = 1;
                if (p1 > p2) return 1;
                else if (p1 < p2) return -1;
                else {
                    return f1.getProperties().getINFRACCION().compareTo(f2.getProperties().getTIPO_SERVICIO());
                }
            }
        });
        KeysMaxPq1(comparableF);
        this.pq2 = new MaxPQ<>(pq, new Comparator<Features>(){
            @Override
            public int compare(Features f1, Features f2) {
                double latitud = 4.647586;
                double longitud = 74.078122;
                double latF1 = f1.getGeometry().DarCoordenadas().get(0);
                double latF2 = f2.getGeometry().DarCoordenadas().get(0);
                double longF1 = f1.getGeometry().DarCoordenadas().get(1);
                double longF2 = f2.getGeometry().DarCoordenadas().get(1);
                int p1 = 0;
                int p2 = 0;
                double d1 = AvDistance.distance(latitud,longitud,latF1,longF1);
                double d2 = AvDistance.distance(latitud,longitud,latF2,longF2);
                if (0 < d1 && d1 < 0.5)
                    p1=5;
                else if (0.51<d1 && d1 < 1.0)
                    p1=4;
                else if (1.1<d1 && d1<1.5)
                    p1=3;
                else if (1.51<d1 && d1<2.0)
                    p1=2;
                else p1=1;
                if (0 < d2 && d2 < 0.5)
                    p2=5;
                else if (0.51<d2 && d2 < 1.0)
                    p2=4;
                else if (1.1<d2 && d2<1.5)
                    p2=3;
                else if (1.51<d2 && d2<2.0)
                    p2=2;
                else p2=1;
                if (p1 > p2) return 1;
                else if (p1 < p2) return -1;
                else {
                    return f1.getProperties().getINFRACCION().compareTo(f2.getProperties().getTIPO_SERVICIO());
                }
            }
        });
        KeysMaxPq2(comparableF);
    }
    public void RBTS(Features[] list){
        this.rbt1 = new RedBlackBST<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String f1 = (String) o1;
                String f2 = (String) o2;

                String mods = f1.replace('(','\0' );
                mods = mods.replace(')','\0');
                String[] temp = mods.split(",");
                String local = temp[0];
                String fechaHora = temp[1].replace(':','-' );
                fechaHora = fechaHora.replace('T','-' );
                fechaHora = fechaHora.replace('Z','\0' );
                String[] values = fechaHora.split("-");
                int anio = Integer.parseInt(values[0]);
                int mes = Integer.parseInt(values[1]);
                int dia = Integer.parseInt(values[2]);
                int hora = Integer.parseInt(values[3]);
                int min = Integer.parseInt(values[4]);
                int sec = (int) Double.parseDouble(values[5]);
                String modsC = f2.replace('(','\0' );
                modsC = modsC.replace(')','\0');
                String[] temp1 = modsC.split(",");
                String localC = temp1[0];
                String fechaHoraC =temp1[1].replace(':','-' );
                fechaHoraC = fechaHoraC.replace('T','-' );
                fechaHoraC = fechaHoraC.replace('Z','\0' );
                String[] valuesC = fechaHoraC.split("-");
                int aComp = Integer.parseInt(valuesC[0]);
                int mComp = Integer.parseInt(valuesC[1]);
                int dComp = Integer.parseInt(valuesC[2]);
                int hrComp = Integer.parseInt(valuesC[3]);
                int minComp = Integer.parseInt(valuesC[4]);
                int secComp = (int) Double.parseDouble(valuesC[5]);
                Calendar d1 = Calendar.getInstance();
                Calendar d2 = Calendar.getInstance();
                d1.set(anio,mes,dia,hora,min,sec);
                d2.set(aComp,mComp,dComp,hrComp,minComp,secComp);
                int l = local.compareToIgnoreCase(localC);
                if(l==0){
                    return d1.compareTo(d2);
                }
                else{
                    return l;
                }
            }
        });
        KeysRedBlack1(comparableF);
        this.rbt2 = new RedBlackBST<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String f1 = (String) o1;
                String f2 = (String) o2;
                String mods = f1.replace('(','\0' );
                mods = mods.replace(')','\0');
                String[] temp = mods.split(",");
                String vehiculo = temp[0];
                String latitud = temp[1];
                String modsC = f2.replace('(','\0' );
                modsC = modsC.replace(')','\0');
                String[] temp1 = modsC.split(",");
                String vehiculoC = temp1[0];
                String latitudC = temp1[1];
                int v = vehiculo.compareToIgnoreCase(vehiculoC);
                if(v==0){
                    return latitud.compareToIgnoreCase(latitudC);
                }
                else return v;
            }
        });
        KeysRedBlack2(comparableF);
        this.rbt3 = new RedBlackBST<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String f1 = (String) o1;
                String f2 = (String) o2;

                String[] temp = f1.split("-");
                int anio = Integer.parseInt(temp[0]);
                int mes = Integer.parseInt(temp[1]);
                int dia = Integer.parseInt(temp[2]);

                String[] temp1 = f2.split("-");
                int anioC = Integer.parseInt(temp1[0]);
                int mesC = Integer.parseInt(temp1[1]);
                int diaC = Integer.parseInt(temp1[2]);

                Calendar d1 = Calendar.getInstance();
                Calendar d2 = Calendar.getInstance();

                d1.set(anio,mes,dia);
                d2.set(anioC,mesC,diaC);

                return d1.compareTo(d2);

            }
        });
        KeysRedBlack3(comparableF);
    }
    public void HASH(Features[] lsit,Primos primos,int lp,int sc){
        Comparator c1A = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Features f1 = (Features) o1;
                Features f2 = (Features) o2;
                String mods = f1.getProperties().getFECHA_HORA().replace(':', '-');
                mods = mods.replace('T', '-');
                mods = mods.replace('Z', '\0');
                String[] actual = mods.split("-");
                int anio = Integer.parseInt(actual[0]);
                int mes = Integer.parseInt(actual[1]);
                int dia = Integer.parseInt(actual[2]);
                String modsC = f2.getProperties().getFECHA_HORA().replace(':', '-');
                modsC = modsC.replace('T', '-');
                modsC = modsC.replace('Z', '\0');
                String[] comp = modsC.split("-");
                int aComp = Integer.parseInt(comp[0]);
                int mComp = Integer.parseInt(comp[1]);
                int dComp = Integer.parseInt(comp[2]);
                Calendar d1 = Calendar.getInstance();
                Calendar d2 = Calendar.getInstance();
                d1.set(anio, mes, dia);
                d2.set(aComp, mComp, dComp);
                return d1.compareTo(d2);
            }
        };
        Quick.sort(comparableF,c1A);
        this.lp = new LinearProbingHashST<>(lp,primos);
        KeysLp(comparableF,c1A);
        Comparator c1B = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Features f1 = (Features) o1;
                Features f2 = (Features) o2;
                Llave keyMaker = new Llave();
                String k1 = keyMaker.keyReq2BComp(f1);
                String k2 = keyMaker.keyReq2BComp(f2);
                return k1.compareTo(k2);
            }
        };
        Quick.sort(comparableF,c1B);
        this.sc = new SeparateChainingHashST<>(sc,primos);
        KeysSC(comparableF,c1B);
    }

    public void KeysMaxPq1(Features[] list){
        for(int i=0;i<list.length;i++){
            pq1.put(list[i]);
        }
    }
    public void KeysMaxPq2(Features[] list){
        for(int i=0;i<list.length;i++){
            pq2.put(list[i]);
        }
    }
    public void KeysRedBlack1(Features[] list){
            Llave keyMaker = new Llave();
            for (int i =0 ; i< list.length;i++){
                String key = keyMaker.keyReq3A(list[i]);
                Features value = list[i];
                rbt1.put(key,value);
            }

    }
    public void KeysRedBlack2(Features[] list){
        Llave keyMaker = new Llave();
        for(int i=0; i< list.length;i++){
            String key = keyMaker.keyReq3B(list[i]);
            Features value = list[i];
            rbt2.put(key,value);
        }
    }
    public void KeysRedBlack3(Features[] list){
        Llave keyMaker = new Llave();
        for(int i=0; i< list.length;i++){
            String key = keyMaker.keyReq1C(list[i]);
            Features value = list[i];
            rbt3.put(key,value);
        }
    }
    public void KeysLp(Features[] list,Comparator c){
        Llave dataPair = new Llave();
        int l=0;
        for(int j=0;j<list.length;j++){
            int comp = 0;
            if (!(j == list.length-1)) comp = c.compare(list[j],list[j+1]);
            if (comp != 0) {
                String key = dataPair.keyReq2A(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                   valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                lp.put(key,values);
                dataPair.getArr().clear();
            }
            if (j == list.length-1){
                String key = dataPair.keyReq2A(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                lp.put(key,values);
                dataPair.getArr().clear();
            }

        }

    }
    public void KeysSC(Features[] list,Comparator c){
        Llave dataPair = new Llave();
        int l=0;
        for(int j=0;j<list.length;j++){
            int comp = 0;
            if (!(j == list.length-1)) comp = c.compare(list[j],list[j+1]);
            if (comp != 0) {
                String key = dataPair.keyReq2B(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                sc.put(key,values);
                dataPair.getArr().clear();
            }
            if (j == list.length-1){
                String key = dataPair.keyReq2B(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                sc.put(key,values);
                dataPair.getArr().clear();
            }

        }

    }
    public Features[] Req1A(int M){
        Features[] values = new Features[M];
        for(int i=0;i<M ; i++)
            values[i] = pq1.delMax();
        return values;
    }
    public Features[][] Req2A(int Month, String Day){
        Calendar  c = Calendar.getInstance();
        int day = 0;
        if(Day.equalsIgnoreCase("D"))
            day=1;
        else if(Day.equalsIgnoreCase("L"))
            day=2;
        else if(Day.equalsIgnoreCase("M"))
            day=3;
        else if(Day.equalsIgnoreCase("I"))
            day=4;
        else if(Day.equalsIgnoreCase("J"))
            day=5;
        else if(Day.equalsIgnoreCase("V"))
            day=6;
        else if(Day.equalsIgnoreCase("S"))
            day=7;
        else day =-1;
        c.set(Calendar.YEAR,2018);
        c.set(Calendar.MONTH, Month-1);
        c.set(Calendar.WEEK_OF_MONTH,1);
        c.set(Calendar.DAY_OF_WEEK, day);
        int Rday = c.get(Calendar.DATE);
        String dateToSearch1 = "";
        String dateToSearch2 = "";
        String dateToSearch3 = "";
        String dateToSearch4 = "";
        if (Month < 10){
            dateToSearch1 = "2018-0"+Month+"-"+Rday;
            c.set(Calendar.WEEK_OF_MONTH,2);
            c.set(Calendar.DAY_OF_WEEK, day);
            Rday = c.get(Calendar.DATE);
            dateToSearch2 = "2018-0"+Month+"-"+Rday;
            c.set(Calendar.WEEK_OF_MONTH,3);
            c.set(Calendar.DAY_OF_WEEK, day);
            Rday = c.get(Calendar.DATE);
            dateToSearch3 = "2018-0"+Month+"-"+Rday;
            c.set(Calendar.WEEK_OF_MONTH,4);
            c.set(Calendar.DAY_OF_WEEK, day);
            Rday = c.get(Calendar.DATE);
            dateToSearch4 = "2018-0"+Month+"-"+Rday;
        }
        else{
            dateToSearch1 = "2018-"+Month+"-"+Rday;
            c.set(Calendar.WEEK_OF_MONTH,2);
            c.set(Calendar.DAY_OF_WEEK, day);
            Rday = c.get(Calendar.DATE);
            dateToSearch2 = "2018-"+Month+"-"+Rday;
            c.set(Calendar.WEEK_OF_MONTH,3);
            c.set(Calendar.DAY_OF_WEEK, day);
            Rday = c.get(Calendar.DATE);
            dateToSearch3 = "2018-"+Month+"-"+Rday;
            c.set(Calendar.WEEK_OF_MONTH,4);
            c.set(Calendar.DAY_OF_WEEK, day);
            Rday = c.get(Calendar.DATE);
            dateToSearch4 = "2018-"+Month+"-"+Rday;

        }
        Features[] values1 = lp.get(dateToSearch1);
        Features[] values2 = lp.get(dateToSearch2);
        Features[] values3 = lp.get(dateToSearch3);
        Features[] values4 = lp.get(dateToSearch4);
        Features[][] values = new Features[][]{values1,values2,values3,values4};
        return values;
    }
    public ArregloDinamico<Features> Req3A(String sDate,String eDate,String local){
        ArregloDinamico<Features> values = new ArregloDinamico<>(20);
        local = local.toUpperCase();
        String minKey = "("+local+","+sDate+")";
        String maxKey = "("+local+","+eDate+")";
        Iterator iter = this.rbt1.iteratorInRValue(minKey,maxKey);
        while(iter.hasNext()){
            values.agregar((Features)iter.next());
        }
        return values;
    }
    public Features[] Req1B(int M){
        Features[] values = new Features[M];
        for(int i=0;i<M; i++)
            values[i] = pq2.delMax();
        return values;
    }
    public Features[] Req2B(String medDet,String veh,String serv,String local){
        String key = "("+medDet+","+veh+","+serv+","+local+")";
        Features[] values = sc.get(key);
        return values;
    }
    public ArregloDinamico<Features> Req3B(String sLat,String eLat,String veh){
        ArregloDinamico<Features> values = new ArregloDinamico<>(20);
        String minKey = "("+veh+","+sLat+")";
        String maxKey = "("+veh+","+eLat+")";
        Iterator  iter = this.rbt2.iteratorInRValue(minKey,maxKey);
        while(iter.hasNext())
            values.agregar((Features)iter.next());
        return values;
    }
    public Object[] Req1C(int D){
        ArregloDinamico<Integer> values = new ArregloDinamico<>(20);
        ArregloDinamico<String> keys = new ArregloDinamico<>(20);
        Calendar date = Calendar.getInstance();
        date.set(2018,0,1);
        int times = 365/D;
        int mayor = 0;
        int i=1;
        String minKey = "";
        String maxKey = "";
        for(;i<=times;i=i*D) {
            minKey = date.get(Calendar.YEAR) +"-"+ date.get(Calendar.MONTH) + "-"+date.get(Calendar.DATE);
            date.add(Calendar.DATE, i);
            maxKey = date.get(Calendar.YEAR) +"-"+ date.get(Calendar.MONTH) + "-"+date.get(Calendar.DATE);
            String keyA = minKey +" to "+ maxKey;
            Integer valueA = 0;
            Iterator iter = this.rbt3.iteratorInRValue(minKey,maxKey);
            while(iter.hasNext()){ valueA++;}
            if (valueA > mayor) mayor = valueA;
            keys.agregar(keyA);
            values.agregar(valueA);
        }
        if (times % D != 0){
            int min = i++;
            date.add(Calendar.DATE, min);
            minKey = date.get(Calendar.YEAR) +"-"+ date.get(Calendar.MONTH) + "-"+date.get(Calendar.DATE);
            int max = 365 - (D*times);
            date.add(Calendar.DATE,max);
            maxKey = date.get(Calendar.YEAR) +"-"+ date.get(Calendar.MONTH) + "-"+date.get(Calendar.DATE);
            String keyA = minKey +" to "+ maxKey;
            Integer valueA = 0;
            Iterator iter = this.rbt3.iteratorInRValue(minKey,maxKey);
            while(iter.hasNext()){ valueA++;}
            if (valueA > mayor) mayor = valueA;
            keys.agregar(keyA);
            values.agregar(valueA);
        }
        Object[] rta = new Object[]{values,keys,mayor};
        return rta;
    }


    public String getMayorOBJ() {
        mayorObj = this.rbt1.max();
        return mayorObj;
    }
    public String getMinOBJ(){
        minObj = this.rbt1.min();
        return minObj;
    }
    public Double[] getMinMax() {
        return MinMax;
    }

    public int getTamanio() {
        return tamanio;
    }

}

