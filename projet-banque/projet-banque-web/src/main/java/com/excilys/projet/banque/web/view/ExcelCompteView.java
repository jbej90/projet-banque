package com.excilys.projet.banque.web.view;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.DataFormatRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;

public class ExcelCompteView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFSheet sheet;
		HSSFRow sheetRow;
		HSSFCell cell;
		
		// Format numéraire
		short montantFormat = HSSFDataFormat.getBuiltinFormat("#€,##0.00");
		
		// Style numéraire découvert
		HSSFCellStyle styleMontantDecouvert = workbook.createCellStyle();
		styleMontantDecouvert.setFillForegroundColor(HSSFColor.RED.index);
		styleMontantDecouvert.setDataFormat(montantFormat);
		
		// Style numéraire positif
		HSSFCellStyle styleMontant = workbook.createCellStyle();
		styleMontant.setDataFormat(montantFormat);
		

		Compte compte = (Compte) model.get("compte");
		List<Operation> operations = (List<Operation>) model.get("operations");
		List<Operation> operationsCarte = (List<Operation>) model.get("operationsCarte");
		float soustotal = (Float) model.get("soustotal");
		float soustotalCarte = (Float) model.get("soustotalCarte");
		float total = (Float) model.get("total");
		String[] listemois = (String[]) model.get("listemois");
		
		SimpleDateFormat df = new SimpleDateFormat("dd/m/yy");

		// Go to the first sheet
		// getSheetAt: only if wb is created from an existing document
		sheet = workbook.getSheetAt(0);
//		sheet = workbook.createSheet("Spring");
//		sheet.setDefaultColumnWidth(12);

		int i = 0;
		for (Operation operation : operations) {
			setText(getCell(sheet, 6 + i, 0), df.format(operation.getDateOp()));
			setText(getCell(sheet, 6 + i, 1), operation.getLibelle());
			setText(getCell(sheet, 6 + i, 2), operation.getType().name());
			
			if (operation.getMontant() < 0) {
				cell = getCell(sheet, 6 + i, 3);
				cell.setCellStyle(styleMontantDecouvert);
				setText(cell, String.valueOf(operation.getMontant()));
			} else {
				cell = getCell(sheet, 6 + i, 4);
				cell.setCellStyle(styleMontant);
				setText(cell, String.valueOf(operation.getMontant()));
			}

			i++;
		}
	}
}
