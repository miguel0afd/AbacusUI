/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cediant.abacus;

import es.cediant.database.Serie;
import es.cediant.service.ISerieService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author miguel
 */
@ManagedBean
@RequestScoped
public class ReportBean {
    
    final Logger logger = LoggerFactory.getLogger(ReportBean.class);   
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    //Spring User Service is injected...
    @ManagedProperty(value="#{SerieService}")
    ISerieService serieService;

    public ISerieService getSerieService() {
        return serieService;
    }

    public void setSerieService(ISerieService serieService) {
        this.serieService = serieService;
    }
    
    private HashMap<String, ArrayList<Serie>> getSeries() {
        return getSerieService().getSeries();
    }
    
    /**
     *
     * @param actionEvent
     */
    public void createReport() {
        try {
            logger.info("Creating report...");                    
            
            Properties prop = new Properties();
            //prop.load(new FileInputStream(getClass().getResourceAsStream("/hibernate.properties")));
            prop.load(getClass().getResourceAsStream("/hibernate.properties"));
            
            Class.forName(prop.getProperty("hibernate.connection.driverClassName"));        
            
            String url = prop.getProperty("hibernate.connection.url");
            String username = prop.getProperty("hibernate.connection.username");
            String password = prop.getProperty("hibernate.connection.password");        
            
            Connection conn = DriverManager.getConnection(url, username, password);
            
            Date date = new Date();
            
            /*HashMap<String, ArrayList<Serie>> series = getSeries();        
                            
            JRTableModelDataSource jrttds = new JRTableModelDataSource(new CustomDataSource(series));*/
            
            //Connection conn = DatabaseConnection.getInstance().getConnection();              
            
            // Session session = ConnectHibernate.getSessionFactory().getCurrentSession();
            // Session session = SessionFactory.getCurrentSession();
            // Connection conn = session.disconnect();
            
            // UserDAO userDAO = new UserDAO();
            // Session session = userDAO.getSessionFactory().getCurrentSession();   
            
            // Class.forName(DatabaseConnection.getInstance().getDriverName());       
            
            Map <String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "User Report");
            parameters.put("DataFile", "/WEB-INF/reports/report.jrxml"); 
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            InputStream reportStream = facesContext.getExternalContext().getResourceAsStream("/WEB-INF/reports/report.jrxml");        
            
            JasperDesign jasperDesign = JRXmlLoader.load(reportStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);        
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            //JasperViewer.viewReport(jasperPrint, false);
            Path path = Paths.get(facesContext.getExternalContext().getRealPath("/WEB-INF/reports/report.jrxml"));
            String pathStr = path.getParent().toString();
            JasperExportManager.exportReportToPdfFile(jasperPrint, pathStr+"/report.pdf");        
            
            /*JasperDesign jasperDesign = JRXmlLoader.load("reports/report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);        
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jrttds);
            JasperViewer.viewReport(jasperPrint, false);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "reports/report.pdf");*/
                    
            /*ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = classLoader.getResource("/resources.properties").getPath();
            logger.info("Path = "+path);
            for (URL url : Collections.list(Thread.currentThread().getContextClassLoader().getResources(""))) {
                logger.info(url.getPath());
            }                      
            
            
            DocXML doc = new DocXML("/WEB-INF/applicationContext.xml");
            String driver = doc.getPropertyValue("DataSource", "driverClass");
            String url = doc.getPropertyValue("DataSource", "jdbcUrl");
            String user = doc.getPropertyValue("DataSource", "user");
            String passwd = doc.getPropertyValue("DataSource", "password");*/
            
            //Session session = this.sessionFactory.getCurrentSession();       
            /*session.doWork(new Work() {
                @Override
                public void execute(Connection conn) throws SQLException {
                    try {
                        JasperDesign jasperDesign = JRXmlLoader.load("report.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);  
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
                        JasperViewer.viewReport(jasperPrint,false);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, "report.pdf");
                    } catch (JRException ex) {
                        logger.info(ex.getMessage());
                    }
                }            
            });*/
            
            // Class.forName(driver);
            // conn = DriverManager.getConnection(url, user, passwd);
            // Statement stmt = (Statement) conn.createStatement();
            logger.info("Report created.");
            this.setResult("Report created");
        } catch (JRException ex) {      
            logger.error("Jasper report creation failed.");
            logger.error(ex.getMessage());
            this.setResult("Report creation failed");
        } catch (SQLException ex) {
            logger.error("Connection with database failed.");
            logger.error(ex.getMessage());
            this.setResult("Report creation failed");
        } catch (ClassNotFoundException ex) {
            logger.error("Driver Class not found");
            logger.error(ex.getMessage());
            this.setResult("Report creation failed");
        } catch (IOException ex) {
            logger.error("File hibernate.properties not found.");
            logger.error(ex.getMessage());
            this.setResult("Report creation failed");
        }
    }
    
    
}
