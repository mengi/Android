package com.android.myapplication.util;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by menginar on 27.09.2017.
 */

public class DatabaseToExel {

    public AppDatabase appDatabase;
    public boolean state = true;
    public String superCodeName;
    public String reportType;
    List<Stock> stockList;
    List<StockMov> stockMovList;

    public DatabaseToExel(String subName, List<Stock> stocks, List<StockMov> stockMovs, String reportType) {
        this.stockList = stocks;
        this.stockMovList = stockMovs;
        this.superCodeName = subName;
        this.reportType = reportType;
    }

    public DatabaseToExel() {}

    public boolean setReport() {
        File directory = new File(Environment.getExternalStorageDirectory() + "/StokTakipApp/");
        boolean betweenDate = false;

        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        if (!ReportType.PRIVATEREPORT.equals(this.getReportType())) {
            if (!ReportType.BETWEENDATE.equals(this.getReportType())) {
                this.setSuperCodeName(this.getReportType());
            } else {
                betweenDate = true;
            }
        }

        try {

            File file = new File(directory, this.getSuperCodeName());
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("tr", "TR"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);

            if (betweenDate) {
                WritableSheet sheet = workbook.createSheet(this.getSuperCodeName(), 0);

                sheet.addCell(new Label(0, 0, "URUN ADI"));
                sheet.addCell(new Label(1, 0, "URUN ADET"));
                sheet.addCell(new Label(2, 0, "DURUM"));
                sheet.addCell(new Label(3, 0, "TARIH"));
                sheet.addCell(new Label(4, 0, "ACIKLAMA"));

                for (int i = 1; i < this.getStockMovList().size() + 1; i++) {
                    sheet.addCell(new Label(0, i, this.getStockMovList().get(i - 1).getProductName()));
                    sheet.addCell(new Label(1, i, Integer.toString(this.getStockMovList().get(i - 1).getProductNumber())));
                    sheet.addCell(new Label(2, i, this.getStockMovList().get(i - 1).getProState()));
                    sheet.addCell(new Label(3, i, this.getStockMovList().get(i - 1).getDateInOu()));
                    sheet.addCell(new Label(4, i, this.getStockMovList().get(i - 1).getProExplan()));
                }
            } else {
                if (ReportType.DAILYREPORT.equals(this.getSuperCodeName()) ||
                        ReportType.WEEKLYREPORT.equals(this.getSuperCodeName()) ||
                        ReportType.MONTHLYYREPORT.equals(this.getSuperCodeName())) {

                    WritableSheet sheet = workbook.createSheet(this.getSuperCodeName(), 0);

                    sheet.addCell(new Label(0, 0, "URUN ADI"));
                    sheet.addCell(new Label(1, 0, "URUN CIKIS"));
                    sheet.addCell(new Label(2, 0, "URUN GIRIS"));
                    sheet.addCell(new Label(3, 0, "TARIH"));
                    sheet.addCell(new Label(4, 0, "ACIKLAMA"));

                    for (int i = 1; i < this.getStockMovList().size() + 1; i++) {

                        sheet.addCell(new Label(0, i, this.getStockMovList().get(i - 1).getProductName()));

                        if (this.getStockMovList().get(i - 1).getProState().equals(ReportType.INPUT)) {
                            sheet.addCell(new Label(1, i, Integer.toString(0)));
                            sheet.addCell(new Label(2, i, Integer.toString(this.getStockMovList().get(i - 1).getProductNumber())));
                        }

                        if (this.getStockMovList().get(i - 1).getProState().equals(ReportType.OUTPUT)) {
                            sheet.addCell(new Label(1, i, Integer.toString(this.getStockMovList().get(i - 1).getProductNumber())));
                            sheet.addCell(new Label(2, i, Integer.toString(0)));
                        }

                        sheet.addCell(new Label(3, i, this.getStockMovList().get(i - 1).getDateInOu()));
                        sheet.addCell(new Label(4, i, this.getStockMovList().get(i - 1).getProExplan()));

                    }
                }

                if (!ReportType.PRIVATEREPORT.equals(this.getSuperCodeName()) ||
                        ReportType.DATABASEREPORT.equals(this.getSuperCodeName()) ||
                        ReportType.AUTOMATIC.equals(this.getSuperCodeName())) {

                    WritableSheet sheet = workbook.createSheet(this.getSuperCodeName(), 0);

                    sheet.addCell(new Label(0, 0, "URUN ADI"));
                    sheet.addCell(new Label(1, 0, "URUN KODU"));
                    sheet.addCell(new Label(2, 0, "URUN ADET"));
                    sheet.addCell(new Label(3, 0, "ALIS FIYATI (TL)"));
                    sheet.addCell(new Label(4, 0, "SATIS FIYATI (TL)"));
                    sheet.addCell(new Label(5, 0, "TARIH"));

                    for (int i = 1; i < this.getStockList().size() + 1; i++) {

                        sheet.addCell(new Label(0, i, this.getStockList().get(i - 1).getProductName()));
                        sheet.addCell(new Label(1, i, this.getStockList().get(i - 1).getProductCode()));
                        sheet.addCell(new Label(2, i, Integer.toString(this.getStockList().get(i - 1).getProductNumber())));
                        sheet.addCell(new Label(3, i, Double.toString(this.getStockList().get(i - 1).getPruchasePrice())));
                        sheet.addCell(new Label(4, i, Double.toString(this.getStockList().get(i - 1).getSalePrice())));
                        sheet.addCell(new Label(5, i, this.getStockList().get(i - 1).getDateSave()));
                    }
                }
            }

            workbook.write();
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();
            state = false;
        }

        return this.state;
    }

    public boolean setReportDayWeekMonth() {
        boolean betweenDate = true;

        try {

            this.setSuperCodeName(this.getReportType());

            File directory = new File(Environment.getExternalStorageDirectory() + "/StokTakipApp/");

            if (!directory.isDirectory()) {
                directory.mkdirs();
            }

            File file = new File(directory, this.getSuperCodeName());
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("tr", "TR"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);

            WritableSheet sheet = workbook.createSheet(this.getSuperCodeName(), 0);

            sheet.addCell(new Label(0, 0, "URUN ADI"));
            sheet.addCell(new Label(1, 0, "URUN ADET"));
            sheet.addCell(new Label(2, 0, "DURUM"));
            sheet.addCell(new Label(3, 0, "TARIH"));
            sheet.addCell(new Label(4, 0, "ACIKLAMA"));

            for (int i = 1; i < this.getStockMovList().size() + 1; i++) {
                sheet.addCell(new Label(0, i, this.getStockMovList().get(i - 1).getProductName()));
                sheet.addCell(new Label(1, i, Integer.toString(this.getStockMovList().get(i - 1).getProductNumber())));
                sheet.addCell(new Label(2, i, this.getStockMovList().get(i - 1).getProState()));
                sheet.addCell(new Label(3, i, this.getStockMovList().get(i - 1).getDateInOu()));
                sheet.addCell(new Label(4, i, this.getStockMovList().get(i - 1).getProExplan()));
            }

            workbook.write();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            betweenDate = false;
        }

        return betweenDate;
    }

    public boolean getExcelToDatabase(Context context, String fileName) {

        getAppDatabaseConnect(context);
        boolean state = true;

        String myFilePath = Environment.getExternalStorageDirectory().toString() + "/StokTakipApp/" + fileName;

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            state = false;
        }

        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/StokTakipApp/", fileName);
            FileInputStream myInput = new FileInputStream(file);

            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIter = mySheet.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();

                if (myRow.getRowNum() != 0) {
                    Iterator<org.apache.poi.ss.usermodel.Cell> cellIter = myRow.cellIterator();

                    ArrayList<String> strings = new ArrayList<>();
                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        strings.add(myCell.toString());
                    }

                    Stock stock = new Stock();
                    stock.setProductName(strings.get(0));
                    stock.setProductCode(strings.get(1));
                    stock.setProductNumber(Integer.parseInt(strings.get(2)));
                    stock.setPruchasePrice(Double.parseDouble(strings.get(3)));
                    stock.setSalePrice(Double.parseDouble(strings.get(4)));
                    stock.setDateSave(strings.get(5));

                    appDatabase.stockDao().insertStock(stock);
                }
            }
        } catch (Exception e) {
            state = false;
        }

        return state;
    }

    public boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.database_connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getSuperCodeName() {
        return superCodeName;
    }

    public void setSuperCodeName(String superCodeName) {
        this.superCodeName = superCodeName;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public List<StockMov> getStockMovList() {
        return stockMovList;
    }

    public void setStockMovList(List<StockMov> stockMovList) {
        this.stockMovList = stockMovList;
    }
}
