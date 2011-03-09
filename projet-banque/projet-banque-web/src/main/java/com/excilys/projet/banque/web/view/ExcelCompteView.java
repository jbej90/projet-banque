package com.excilys.projet.banque.web.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelCompteView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFSheet sheet;
		HSSFRow sheetRow;
		HSSFCell cell;

		System.err.println("=== Excel ===");

		// Go to the first sheet
		// getSheetAt: only if wb is created from an existing document
		// sheet = wb.getSheetAt( 0 );
		sheet = workbook.createSheet("Spring");
		sheet.setDefaultColumnWidth(12);

		// write a text at A1
		cell = getCell(sheet, 0, 0);
		setText(cell, "Spring-Excel test");

//		List words = (List) model.get("wordList");
//		for (int i = 0; i < words.size(); i++) {
//			cell = getCell(sheet, 2 + i, 0);
//			setText(cell, (String) words.get(i));
//		}
	}
}
