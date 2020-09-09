package com.rocky.pdffileprinthelper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rocky.pdffilehelper.CreatePdf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CreatePdf.PdfCallbackListener {

    override fun onSuccess(filePath: String) {
        Log.i("MainActivity", "Pdf Saved at: $filePath")
        Toast.makeText(this, "Pdf Saved at: $filePath", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    var openPrintDialog: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //check the marshmallow permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                return
            }
        }


        btnPrint.setOnClickListener {
            openPrintDialog = false
            doPrint()
        }

        btnPrintAndSave.setOnClickListener {
            openPrintDialog = true
            doPrint()
        }
    }

    private fun doPrint() {
        CreatePdf(this)
            .setPdfName("Sample")
            .openPrintDialog(openPrintDialog)
            .setContentBaseUrl(null)
            .setPageSize(PrintAttributes.MediaSize.ISO_A4)
            .setFilePath(Environment.getExternalStorageDirectory().absolutePath + "/MyPdf")
            .setContent(template)
            .setCallbackListener(object : CreatePdf.PdfCallbackListener {
                override fun onFailure(errorMsg: String) {
                    Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(filePath: String) {
                    Toast.makeText(this@MainActivity, "Pdf Saved at: $filePath", Toast.LENGTH_SHORT).show()
                }
            })
            .create()
    }

    var template = "<!DOCTYPE html>\n" +
            "<html\n" +
            "\txmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "\t<head>\n" +
            "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\"/>\n" +
            "\t\t<style>            table {border-collapse:collapse; table-layout:auto; width:210mm;}            table td {word-wrap:break-word;}            </style>\n" +
            "\t</head>\n" +
            "\t<body style=\"font-family:sans-serif,Helvetica, Monospace; font-size: 15px; margin: 0;\">\n" +
            "\t\t<table style=\"margin:auto;width: auto; font-size: 15px\">\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>\n" +
            "\t\t\t\t\t<table style=\" color: rgb(0, 0, 0);font-size:15px;width: 100%\">\n" +
            "\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t<td style=\"vertical-align: top;\">\n" +
            "\t\t\t\t\t\t\t\t<table style = \"width: 100%\">\n" +
            "\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left; font-weight: bold;font-size:15px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;padding-left: 5px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<b>quickdine</b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;padding-left: 5px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<b>quickdine</b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;padding-left: 5px;\">Phone No.: 911543115311</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;padding-left: 5px;\">Email: anil.vadla@effiasoft.org</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t<td style=\"vertical-align: top;\">\n" +
            "\t\t\t\t\t\t\t\t<table style=\"width: 100%;\">\n" +
            "\t\t\t\t\t\t\t\t\t<tr></tr>\n" +
            "\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<td style=\"text-align : right;font-weight : bold;font-size: 20px\">                                            ORDER                                        </td>\n" +
            "\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t</table>\n" +
            "\t\t\t\t</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>\n" +
            "\t\t\t\t\t<table style=\"border-top: 2px solid black;border-collapse:collapse;width: 100%;\"  >\n" +
            "\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t<td style=\"vertical-align: top;padding-top: 5px;\">\n" +
            "\t\t\t\t\t\t\t\t<table>\n" +
            "\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<b>NB General Stores.</b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</b>\n" +
            "\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;\">Address: Telang\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<br>ana,Hyderabad,\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;\">Phone No.: 8096645366</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t\t\t\t<table style=\"width: 200%;border-spacing: 0;border-collapse: collapse;\"                                    align=\"right\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align:right;font-size: 15px;font-weight:bold;color: #000000;\">                                            Order No.: ORD/B1/21/20                                        </td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;font-size: 15px;font-weight:bold;color: #000000;\">                                            Order Date: 31-Aug-2020 03:06 pm                                        </td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;font-size: 15px;color: #000000;\">                                            Type: Take Away                                        </td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 15px;font-weight:bold;color: #000000;\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 15px;font-weight:bold;color: #000000;\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 15px;font-weight:bold;color: #000000;\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 15px;font-weight:bold;color: #000000;\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\t\t\t\t<tr>\n" +
            "\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t<table style=\"width: 100%;;margin-top: 10px;border-spacing: 0;border-collapse: collapse;border-top:2px solid #000000;border-bottom:2px solid #000000;\">\n" +
            "\t\t\t\t\t\t\t<tr style=\"background-color: #f1f1f1; height: 20px;\">\n" +
            "\t\t\t\t\t\t\t\t<td style=\"font-size: 10px; font-weight: bold; padding-left: 5px;\">                                Product/Service                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 10px; font-weight: bold; padding-right: 5px;\">                                Quantity                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: center; font-size: 10px; font-weight: bold; padding-left: 5px;\">                                Unit Price                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: center; font-size: 10px; font-weight: bold; padding-left: 5px;\">                                Discount                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 10px; font-weight: bold; padding-right: 5px;\">                                Tax 1                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 10px; font-weight: bold; padding-right: 5px;\">                                Tax 2                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 10px; font-weight: bold; padding-right: 5px;\">                                Tax 3                            </td>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"text-align: right; font-size: 10px; font-weight: bold; padding-right: 5px;\">                                Amount                            </td>\n" +
            "\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;padding-left: 5px;font-size: 10px\" valign=\"top\">Apple Juice</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">1 Each</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;padding-left: 5px;font-size: 10px\" valign=\"top\">₹100.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹100.00</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;padding-left: 5px;font-size: 10px\" valign=\"top\">Avocado Salad</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">1 Each</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;padding-left: 5px;font-size: 10px\" valign=\"top\">₹8.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: center;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹0.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;padding-left: 5px;font-size: 10px\" valign=\"top\">₹8.00</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\t\t\t\t<tr>\n" +
            "\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t<table style=\"width: 100%\" >\n" +
            "\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t<td style=\"vertical-align:top\">\n" +
            "\t\t\t\t\t\t\t\t\t<table style=\"font-size: 16px;width: 100%;\" align=\"left\" valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr style=\"text-align: left\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td>Total No. of Products/Services</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td>2</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr style=\"text-align: left\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td>Total Quantity</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td>2</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left;font-size: 16px;font-weight: bold;\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<td colspan = \"2\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<br>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t\t\t\t\t<table style=\"border-spacing: 0;margin-top: 5px;border-collapse: collapse;font-size: 16px;width: 100%;\" align=\"right\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr style=\"width:100%\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left; font-weight: bold;\">Sub Total :</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;\">₹108.00</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr style=\"width:100%\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left; font-weight: bold;\">Discount on Total :</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;\">-₹10.80</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"border-top: 1px solid #000000; border-top-width: 100%\" colspan=\"2\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left; font-weight: bold;font-size: 16px;\">                                            Grand Total:                                        </td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right;font-size: 16px;\">₹97.20</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\" style=\"text-align: right\">                                            Ninety Seven Rupees  and Twenty Paisa Only                                        </td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<b></b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right\"></td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: left\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<b></b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"text-align: right\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<b></b>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t<table style=\"width:100%\">\n" +
            "\t\t\t\t\t\t\t\t\t<tr style=\"width:100%\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<td colspan=\"1\" align=\"left\"                                    style=\"border-top-width: 5px;border-top: solid black 2px;font-weight: normal;font-size: 16px;\"                                    valign=\"top\">                                    Powered by QJust Billing                                </td>\n" +
            "\t\t\t\t\t\t\t\t\t\t<td colspan=\"1\" align=\"right\"                                    style=\"border-top-width: 5px;border-top: solid black 2px;font-weight: normal;font-size: 16px;\">                                     31-Aug-2020 03:06 pm \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<br> | E &amp; O E                                \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t<tr align=\"right\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<td align=\"right\" style=\"width:100%;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<img src=\"@QRCode@\" width=\"100px\" style=\"float:right;\"/>\n" +
            "\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t</table>\n" +
            "\t\t\t\t</body>\n" +
            "\t\t\t</html>"
}