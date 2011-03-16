package com.excilys.projet.banque.web.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;

public class ExcelCompteViewPOIColor extends AbstractExcelView {
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFSheet sheet;

		// creating a custom palette for the workbook
		HSSFPalette palette = workbook.getCustomPalette();
		palette.setColorAtIndex(HSSFColor.BLUE_GREY.index, (byte) 249, (byte) 249, (byte) 249);
		palette.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte) 240, (byte) 240, (byte) 240);

		Compte compte = (Compte) model.get("compte");
		List<Operation> operations = (List<Operation>) model.get("operations");
		List<Operation> operationsCarte = (List<Operation>) model.get("operationsCarte");
		float soustotal = (Float) model.get("soustotal");
		float soustotalCarte = (Float) model.get("soustotalCarte");
		float total = (Float) model.get("total");
		String[] listemois = (String[]) model.get("listemois");

		HSSFRow row;

		// Go to the first sheet
		// getSheetAt: only if wb is created from an existing document
		sheet = workbook.getSheetAt(0);

		getCell(sheet, 2, 2).setCellValue(compte.getLibelle());
		getCell(sheet, 3, 2).setCellValue(compte.getSolde());

		int i = 0;
		for (Operation operation : operations) {
			row = sheet.createRow(6 + i);

			getCellOperation(row, 0).setCellValue(operation.getDateOp());
			getCellOperation(row, 1).setCellValue(operation.getLibelle());
			getCellOperation(row, 2).setCellValue(operation.getType().name());

			if (operation.getMontant() < 0) {
				getCellOperation(row, 3).setCellValue(operation.getMontant());
				getCellOperation(row, 4).setCellValue("");
			}
			else {
				getCellOperation(row, 3).setCellValue("");
				getCellOperation(row, 4).setCellValue(operation.getMontant());
			}

			i++;
		}
	}
	
	protected HSSFCell getCellOperation(HSSFRow row, int col) {
		HSSFCell cell = row.createCell(col);
		HSSFCellStyle style = row.getSheet().getWorkbook().createCellStyle();

		if (row.getRowNum() % 2 == 0) {
			style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		}
		else {
			style.setFillForegroundColor(HSSFColor.WHITE.index);
		}
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		
		return cell;
	}
}
