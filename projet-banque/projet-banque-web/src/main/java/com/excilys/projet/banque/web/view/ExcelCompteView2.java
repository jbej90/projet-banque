package com.excilys.projet.banque.web.view;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;

public class ExcelCompteView2 extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFSheet sheet;

		int annee = (Integer) model.get("annee");
		String mois = (String) model.get("mois");
		Compte compte = (Compte) model.get("compte");
		List<Operation> operations = (List<Operation>) model.get("operations");
		float soustotal = (Float) model.get("soustotal");
		float soustotalCarte = (Float) model.get("soustotalCarte");
		float total = (Float) model.get("total");

		// Go to the first sheet
		// getSheetAt: only if wb is created from an existing document
		sheet = workbook.getSheetAt(0);
		// sheet = workbook.createSheet(compte.getLibelle());
		// sheet.setDefaultColumnWidth(12);
		
		getCell(sheet, 0, 0).setCellValue("Détail de mon compte ("+mois+" "+annee+")");
		getCell(sheet, 2, 2).setCellValue(compte.getLibelle());
		getCell(sheet, 3, 2).setCellValue(compte.getSolde());

		int i = 6;
		for (Operation operation : operations) {
			getCell(sheet, i, 0).setCellValue(operation.getDateOp());
			getCell(sheet, i, 1).setCellValue(operation.getLibelle());
			getCell(sheet, i, 2).setCellValue(operation.getType().name());

			if (operation.getMontant() < 0) {
				getCell(sheet, i, 3).setCellValue(operation.getMontant());
			}
			else {
				getCell(sheet, i, 4).setCellValue(operation.getMontant());
			}

			i++;
		}
		
		getCell(sheet, i, 0).setCellValue("Opérations");
		getCell(sheet, i, soustotal < 0 ? 3 : 4).setCellValue(soustotal);

		i++;
		getCell(sheet, i, 0).setCellValue("Opérations par carte");
		getCell(sheet, i, soustotalCarte < 0 ? 3 : 4).setCellValue(soustotalCarte);

		i++;
		getCell(sheet, i, 0).setCellValue("Total");
		getCell(sheet, i, total < 0 ? 3 : 4).setCellValue(total);
	}
}
