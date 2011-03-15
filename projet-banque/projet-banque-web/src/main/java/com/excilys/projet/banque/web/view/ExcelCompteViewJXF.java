package com.excilys.projet.banque.web.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.biff.EmptyCell;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;

public class ExcelCompteViewJXF extends AbstractJExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WritableSheet sheet;

		int annee = (Integer) model.get("annee");
		String mois = (String) model.get("mois");
		Compte compte = (Compte) model.get("compte");
		List<Operation> operations = (List<Operation>) model.get("operations");
		float soustotal = (Float) model.get("soustotal");
		float soustotalCarte = (Float) model.get("soustotalCarte");
		float total = (Float) model.get("total");
		
		sheet = workbook.getSheet(0);
		
		
		EmptyCell a = new EmptyCell(0, 0);
		Label cell = (Label) sheet.getCell(6, 0);
		cell.setString("aaaa");
		
		/*
		addCell(sheet, 0, 0, "Détail de mon compte ("+mois+" "+annee+")");
		addCell(sheet, 2, 2, compte.getLibelle());
		addCellNumber(sheet, 3, 2, compte.getSolde());

		int i = 6;
		for (Operation operation : operations) {
			addCellDate(sheet, i, 0, operation.getDateOp());
			addCell(sheet, i, 1, operation.getLibelle());
			addCell(sheet, i, 2, operation.getType().name());

			if (operation.getMontant() < 0) {
				addCellNumber(sheet, i, 3, operation.getMontant());
			}
			else {
				addCellNumber(sheet, i, 4, operation.getMontant());
			}

			i++;
		}
		
		addCell(sheet, i, 0, "Opérations");
		addCellNumber(sheet, i, soustotal < 0 ? 3 : 4, soustotal);

		i++;
		addCell(sheet, i, 0, "Opérations par carte");
		addCellNumber(sheet, i, soustotalCarte < 0 ? 3 : 4, soustotalCarte);

		i++;
		addCell(sheet, i, 0, "Total");
		addCellNumber(sheet, i, total < 0 ? 3 : 4, total);
		*/
	}
	
	private Label addCell(WritableSheet sheet, int row, int col, String value) throws RowsExceededException, WriteException {
		Label cell = new Label(row, col, value);
		sheet.addCell(cell);
		return cell;
	}
	private Number addCellNumber(WritableSheet sheet, int row, int col, double value) throws RowsExceededException, WriteException {
		Number cell = new Number(row, col, value);
		sheet.addCell(cell);
		return cell;
	}
	private DateTime addCellDate(WritableSheet sheet, int row, int col, Date value) throws RowsExceededException, WriteException {
		DateTime cell = new DateTime(row, col, value);
		sheet.addCell(cell);
		return cell;
	}
}
