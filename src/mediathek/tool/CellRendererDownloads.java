/*    
 *    MediathekView
 *    Copyright (C) 2008   W. Xaver
 *    W.Xaver[at]googlemail.com
 *    http://zdfmediathk.sourceforge.net/
 *    
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import mediathek.controller.starter.Start;
import mediathek.daten.DatenDownload;
import mediathek.res.GetIcon;

public class CellRendererDownloads extends DefaultTableCellRenderer {

    private static ImageIcon ja_16 = null;
    private static ImageIcon nein_12 = null;
    private static ImageIcon film_play_tab = null;
    private static ImageIcon download_stop_tab = null;
    private static ImageIcon download_start_tab = null;
    private static ImageIcon film_play_sw_tab = null;
    private static ImageIcon download_stop_sw_tab = null;
    private static ImageIcon download_start_sw_tab = null;
    private static ImageIcon download_clear_tab = null;
    private static ImageIcon download_clear_sw_tab = null;
    private static ImageIcon download_del_tab = null;
    private static ImageIcon download_del_sw_tab = null;

    public CellRendererDownloads() {
        ja_16 = GetIcon.getIcon("ja_16.png");
        nein_12 = GetIcon.getIcon("nein_12.png");
        film_play_tab = GetIcon.getIcon("film_play_tab.png");
        film_play_sw_tab = GetIcon.getIcon("film_play_sw_tab.png");
        download_stop_tab = GetIcon.getIcon("download_stop_tab.png");
        download_stop_sw_tab = GetIcon.getIcon("download_stop_sw_tab.png");
        download_start_tab = GetIcon.getIcon("download_start_tab.png");
        download_start_sw_tab = GetIcon.getIcon("download_start_sw_tab.png");
        download_clear_tab = GetIcon.getIcon("download_clear_tab.png");
        download_clear_sw_tab = GetIcon.getIcon("download_clear_sw_tab.png");
        download_del_tab = GetIcon.getIcon("download_del_tab.png");
        download_del_sw_tab = GetIcon.getIcon("download_del_sw_tab.png");
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        try {
            setBackground(null);
            setForeground(null);
            setFont(null);
            setIcon(null);
            setHorizontalAlignment(SwingConstants.LEADING);
            super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            int r = table.convertRowIndexToModel(row);
            int c = table.convertColumnIndexToModel(column);
            DatenDownload datenDownload = (DatenDownload) table.getModel().getValueAt(r, DatenDownload.DOWNLOAD_REF_NR);
            if (c == DatenDownload.DOWNLOAD_PROGRESS_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                    if (1 < datenDownload.start.percent && datenDownload.start.percent < Start.PROGRESS_FERTIG) {
                        JProgressBar progressBar = new JProgressBar(0, 1000);
                        progressBar.setBorder(BorderFactory.createEmptyBorder());
                        progressBar.setStringPainted(true);
                        JPanel panel = new JPanel(new BorderLayout());
                        panel.setBorder(BorderFactory.createEmptyBorder());
                        panel.add(progressBar);
                        progressBar.setUI(new BasicProgressBarUI() {
                            @Override
                            protected Color getSelectionBackground() {
                                return UIManager.getDefaults().getColor("Table.foreground");
                            }

                            @Override
                            protected Color getSelectionForeground() {
                                return Color.white;
                            }
                        });
                        setColor(panel, datenDownload.start, isSelected);
                        setColor(progressBar, datenDownload.start, isSelected);
                        progressBar.setValue(datenDownload.start.percent);
                        double d = datenDownload.start.percent / 10.0;
                        progressBar.setString(Double.toString(d) + "%");
                        return panel;
                    } else {
                        this.setText(Start.getTextProgress(datenDownload.start));
                    }
                } else {
                    this.setText("");
                }
            } else if (c == DatenDownload.DOWNLOAD_RESTZEIT_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                    if (datenDownload.start.beginnAnschauen) {
                        setForeground(GuiKonstanten.ANSEHEN);
                    }
                }
            } else if (c == DatenDownload.DOWNLOAD_NR_NR || c == DatenDownload.DOWNLOAD_DATUM_NR
                    || c == DatenDownload.DOWNLOAD_ZEIT_NR || c == DatenDownload.DOWNLOAD_DAUER_NR
                    || c == DatenDownload.DOWNLOAD_BANDBREITE_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                }
            } else if (c == DatenDownload.DOWNLOAD_GROESSE_NR) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                }
            } else if (c == DatenDownload.DOWNLOAD_ABO_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                }
                if (!datenDownload.arr[DatenDownload.DOWNLOAD_ABO_NR].equals("")) {
                    setForeground(GuiKonstanten.ABO_FOREGROUND);
                } else {
                    setForeground(GuiKonstanten.DOWNLOAD_FOREGROUND);
                    setText("Download");
                    //setIcon(GetIcon.getIcon("nein_12.png"));
                }
            } else if (c == DatenDownload.DOWNLOAD_PROGRAMM_RESTART_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                }
                if (datenDownload.isRestart()) {
                    setIcon(ja_16);
                } else {
                    setIcon(nein_12);
                }
            } else if (c == DatenDownload.DOWNLOAD_BUTTON_START_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                }
                if (isSelected) {
                    if (datenDownload.start != null) {
                        if (datenDownload.start.status >= Start.STATUS_FERTIG) {
                            setIcon(film_play_tab);
                        } else {
                            setIcon(download_stop_tab);
                        }
                    } else {
                        setIcon(download_start_tab);
                    }
                } else {
                    if (datenDownload.start != null) {
                        if (datenDownload.start.status >= Start.STATUS_FERTIG) {
                            setIcon(film_play_sw_tab);
                        } else {
                            setIcon(download_stop_sw_tab);
                        }
                    } else {
                        setIcon(download_start_sw_tab);
                    }
                }
            } else if (c == DatenDownload.DOWNLOAD_BUTTON_DEL_NR) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                    if (datenDownload.start.status >= Start.STATUS_FERTIG) {
                        if (isSelected) {
                            setIcon(download_clear_tab);
                        } else {
                            setIcon(download_clear_sw_tab);
                        }
                    } else if (isSelected) {
                        setIcon(download_del_tab);
                    } else {
                        setIcon(download_del_sw_tab);
                    }
                } else if (isSelected) {
                    setIcon(download_del_tab);
                } else {
                    setIcon(download_del_sw_tab);
                }
            } else {
                if (datenDownload.start != null) {
                    setColor(this, datenDownload.start, isSelected);
                }
            }
        } catch (Exception ex) {
            Log.fehlerMeldung(758200166, Log.FEHLER_ART_PROG, this.getClass().getName(), ex);
        }
        if (isSelected) {
            setFont(new java.awt.Font("Dialog", Font.BOLD, 12));
        } else {
            setFont(new java.awt.Font(null));
        }
        return this;
    }

    private void setColor(Component c, Start s, boolean isSelected) {
        switch (s.status) {
            case Start.STATUS_INIT:
                if (isSelected) {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_WAIT_SEL);
                } else {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_WAIT);
                }
                break;
            case Start.STATUS_RUN:
                if (isSelected) {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_RUN_SEL);
                } else {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_RUN);
                }
                break;
            case Start.STATUS_FERTIG:
                if (isSelected) {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_FERTIG_SEL);
                } else {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_FERTIG);
                }
                break;
            case Start.STATUS_ERR:
                if (isSelected) {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_ERR_SEL);
                } else {
                    c.setBackground(GuiKonstanten.DOWNLOAD_FARBE_ERR);
                }
                break;
        }
    }
}
