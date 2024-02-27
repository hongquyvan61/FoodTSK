package foodtsk;


import Dao.BillDao;
import Dao.BillDetailDao;
import Dao.CategoryDao;
import Dao.ProductDao;
import Dao.UserDao;
import Dao.NhapKhoDao;
import Model.Bill;
import Model.BillDetail;
import Model.Category;
import Model.Product;
import Model.Role;
import Model.User;
import Model.NhapKho;
import com.mysql.cj.jdbc.Blob;
import event.ClickAddCart;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TSang
 */
public class Home extends javax.swing.JFrame {
//static private JLabel[] drinkLabel;
    /**
     * Creates new form Home
     */
    public Home() throws Exception {
        
        initComponents();
        
         jTableListCart.getColumnModel().getColumn(0).setPreferredWidth(5);
         jTableListCart.getColumnModel().getColumn(3).setPreferredWidth(100);
         fillCategory();
         fillDate();
         int id=1;
         showPanel(id);
         loadListUser();
         loadListProduct();
         LoadListBill();
         loadListWarehouseIn();
         loadListWarehouseOut();
         jComboBoxCategoty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxCategoty.getSelectedItem().equals("Food")){
                    try {
                        showPanel(1);
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }else if(jComboBoxCategoty.getSelectedItem().equals("Drink")){
                    try {
                        showPanel(2);
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        
            }
         });
         
           jComboBoxCategoty1.addActionListener(new ActionListener() {   //ComboBox của panel product mng
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDao productDao=new ProductDao();
                 ProductDao dao=new ProductDao();
                  TBLProduct.removeAll();
                    TBLProduct.revalidate();
                    TBLProduct.repaint();
		 DefaultTableModel defaultTableModel=new DefaultTableModel();
		 TBLProduct.setModel(defaultTableModel);
                 defaultTableModel.addColumn("id");
		 defaultTableModel.addColumn("name");
		 defaultTableModel.addColumn("Category");
                 defaultTableModel.addColumn("quantity");
                 defaultTableModel.addColumn("price");
                ArrayList<Product> products=new ArrayList<>();
                if (jComboBoxCategoty1.getSelectedItem().equals("Food")){
                    try {
                        products=productDao.getAllRows(1);
                         for(Product product:products) {
                     
                            String name=product.getName();
                            int cat=product.getCategoryID();
                            int quantity=product.getQuantity();
                            int price=product.getPrice();
                            String categoryString=null;
                         if (product.getCategoryID()==1) {
                            categoryString="Food";
                         }else{
                          categoryString="Drink";
                         }
                          defaultTableModel.addRow(new Object[] { product.getId(),name,categoryString,quantity,price});
                     }
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }else if(jComboBoxCategoty1.getSelectedItem().equals("Drink")){
                    try {
                          products=productDao.getAllRows(2);
                           for(Product product:products) {
                     
			 String name=product.getName();
                         int cat=product.getCategoryID();
                         int quantity=product.getQuantity();
                         int price=product.getPrice();
                         String categoryString=null;
                         if (product.getCategoryID()==1) {
                            categoryString="Food";
                         }else{
                          categoryString="Drink";
                         }
                          defaultTableModel.addRow(new Object[] { product.getId(),name,categoryString,quantity,price});
                     }
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    try {
                        products=productDao.getAllRows();
                        loadListProduct();
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        
            }
         });
       jComboBoxDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    TBLHistory.removeAll();
                    TBLHistory.revalidate();
                    TBLHistory.repaint();
                    
                     if (jComboBoxDate.getSelectedItem().equals("All Date")) {
                        try {
                            LoadListBill();
                        } catch (Exception ex) {
                            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }else{
                        DefaultTableModel defaultTableModel=new DefaultTableModel();
                        TBLHistory.setModel(defaultTableModel);
                        defaultTableModel.addColumn("ID Bill");
                        defaultTableModel.addColumn("Time");
                        defaultTableModel.addColumn("Date");
                        defaultTableModel.addColumn("Total");
                        BillDao bd=new BillDao();
                        String date=String.valueOf(jComboBoxDate.getSelectedItem());
                    try {
                        ArrayList<Bill> bills=new ArrayList<Bill>();
                         bills=bd.getAllRowsDate(date);
                         /*System.out.println(bills.get(0).getTotal());*/
                        for(Bill bill:bills){
                             defaultTableModel.addRow(new Object[] { bill.getId(),bill.getTime(),bill.getDate(),bill.getTotal()});
                        }
                        int rowHistory=TBLHistory.getRowCount();
                        if (rowHistory>0) {
                            double total=0;
                            for (int row = 0; row < rowHistory; row++) {
                               total+=Double.parseDouble(String.valueOf(TBLHistory.getValueAt(row, 3)) ) ;

                           }
            DecimalFormat formatter = new DecimalFormat("###,###,###");
              jTextFieldRevenue.setText(formatter.format(total)+" VNĐ");
        }
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                         }

            }
       } );
          
         
        
        setColor(btn_home);
        setVisiblePanel(JPanelHome);
          
        //details
       
       
//        TBLHistory.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e) {
//               if (e.getClickCount() == 2) {     // to detect doble click events
//                  
//                    int row = TBLHistory.getSelectedRow();
//                    int id=Integer.parseInt(String.valueOf(TBLHistory.getValueAt(row, 0)) ) ;
//                    System.out.println(id);
//            }
//            }
//        });
    
}
//    public void RefreshTable(){
//        
//        TBLUser.removeAll();
//        TBLUser.revalidate();
//        TBLUser.repaint();
//    }
     public void loadListUser() throws Exception{
          UserDao dao=new UserDao();
		 DefaultTableModel defaultTableModel=new DefaultTableModel();
		 TBLUser.setModel(defaultTableModel);
                 TBLUser.setDefaultEditor(Object.class, null);
                 defaultTableModel.addColumn("id");
		 defaultTableModel.addColumn("name");
		 defaultTableModel.addColumn("username");
                 defaultTableModel.addColumn("sex");
                 defaultTableModel.addColumn("role");
		 List<User> users=dao.getAllRows();
		 for(User user:users) {
                     
			 String name=user.getName();
                         String username=user.getUsername();
                         String sex=user.getSex();
                         String role=null;
                         if (user.getRole()==2) {
                         role="admin";
                         }else{
                          role="employee";
                         }
                          defaultTableModel.addRow(new Object[] { user.getId(),name,username,sex,role});
                     }
			
    }
     public void LoadListBill() throws Exception{
               TBLHistory.removeAll();
                TBLHistory.revalidate();
                TBLHistory.repaint();
                BillDao dao=new BillDao();
		 DefaultTableModel defaultTableModel=new DefaultTableModel();
		 TBLHistory.setModel(defaultTableModel);
                 defaultTableModel.addColumn("ID Bill");
		 defaultTableModel.addColumn("Time");
		 defaultTableModel.addColumn("Date");
                 defaultTableModel.addColumn("Total");
		 ArrayList<Bill> bills=dao.getAllRows();
                    
                  for(Bill bill:bills) {
                        
                          defaultTableModel.addRow(new Object[] { bill.getId(),bill.getTime(),bill.getDate(),bill.getTotal()});
                     }
         int rowHistory=TBLHistory.getRowCount();
         if (rowHistory>0) {
             double total=0;
             for (int row = 0; row < rowHistory; row++) {
                total+=Double.parseDouble(String.valueOf(TBLHistory.getValueAt(row, 3)) ) ;
                
            }
            DecimalFormat formatter = new DecimalFormat("###,###,###");
              jTextFieldRevenue.setText(formatter.format(total)+" VNĐ");
        }
     }
      public void loadListProduct() throws Exception{
                TBLProduct.removeAll();
                TBLProduct.revalidate();
                TBLProduct.repaint();
                ProductDao dao=new ProductDao();
		 DefaultTableModel defaultTableModel=new DefaultTableModel();
		 TBLProduct.setModel(defaultTableModel);
                 TBLProduct.setDefaultEditor(Object.class, null);
                 defaultTableModel.addColumn("id");
		 defaultTableModel.addColumn("name");
		 defaultTableModel.addColumn("Category");
                 defaultTableModel.addColumn("quantity");
                 defaultTableModel.addColumn("price");
		 List<Product> products=dao.getAllRows();
		 for(Product product:products) {
                     
			 String name=product.getName();
                         int cat=product.getCategoryID();
                         int quantity=product.getQuantity();
                         int price=product.getPrice();
                         String categoryString=null;
                         if (product.getCategoryID()==1) {
                            categoryString="Food";
                         }else{
                          categoryString="Drink";
                         }
                          defaultTableModel.addRow(new Object[] { product.getId(),name,categoryString,quantity,price});
                     }
			
    }
    public void loadListWarehouseIn() throws Exception{
        tablenhap.removeAll();
        tablenhap.revalidate();
        tablenhap.repaint();
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        tablenhap.setModel(defaultTableModel);
        tablenhap.setDefaultEditor(Object.class, null);
        defaultTableModel.addColumn("id");
        defaultTableModel.addColumn("quantity");
        defaultTableModel.addColumn("price");
        defaultTableModel.addColumn("date");
        ArrayList<NhapKho> list = new ArrayList<NhapKho>();
        NhapKhoDao nkdao = new NhapKhoDao();
        list = nkdao.getAllRows();
        for(int i=0;i<list.size();i++){
            defaultTableModel.addRow(new Object[]{list.get(i).getProduct(),list.get(i).getQuantity(),list.get(i).getPrice(),list.get(i).getDate()});
        }
    }
    public void loadListWarehouseOut() throws Exception{
        /*tablenhap.removeAll();
        tablenhap.revalidate();
        tablenhap.repaint();
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        tablenhap.setModel(defaultTableModel);
        tablenhap.setDefaultEditor(Object.class, null);
        defaultTableModel.addColumn("id");
        defaultTableModel.addColumn("quantity");
        defaultTableModel.addColumn("price");
        defaultTableModel.addColumn("date");
        ArrayList<NhapKho> list = new ArrayList<NhapKho>();
        NhapKhoDao nkdao = new NhapKhoDao();
        list = nkdao.getAllRows();
        for(int i=0;i<list.size();i++){
            defaultTableModel.addRow(new Object[]{list.get(i).getProduct(),list.get(i).getQuantity(),list.get(i).getPrice(),list.get(i).getDate()});
        }*/
        tablexuat.removeAll();
                tablexuat.revalidate();
                tablexuat.repaint();
                ProductDao dao=new ProductDao();
		 DefaultTableModel defaultTableModel=new DefaultTableModel();
		tablexuat.setModel(defaultTableModel);
                 tablexuat.setDefaultEditor(Object.class, null);
                 defaultTableModel.addColumn("id");
		 defaultTableModel.addColumn("name");
		 defaultTableModel.addColumn("Category");
                 defaultTableModel.addColumn("quantity");
                 defaultTableModel.addColumn("price");
		 List<Product> products=dao.getAllRows();
		 for(Product product:products) {
                     
			 String name=product.getName();
                         int cat=product.getCategoryID();
                         int quantity=product.getQuantity();
                         int price=product.getPrice();
                         String categoryString=null;
                         if (product.getCategoryID()==1) {
                            categoryString="Food";
                         }else{
                          categoryString="Drink";
                         }
                          defaultTableModel.addRow(new Object[] { product.getId(),name,categoryString,quantity,price});
                     }
    }
    private void fillCategory() throws Exception {
        CategoryDao categoryDao=new CategoryDao();
        ArrayList<Category> listCat=categoryDao.getAllRows();
         jComboBoxCategoty1.addItem("All");
        for(int i=0;i<listCat.size();i++){
            jComboBoxCategoty.addItem(listCat.get(i).getNam());
            jComboBoxCategoty1.addItem(listCat.get(i).getNam());
        }
        
       
        
    }
    private void fillDate() throws Exception{
        BillDetailDao dao=new BillDetailDao();
        ArrayList<String> listDate=dao.getDate();
         jComboBoxDate.addItem("All Date");
        for(int i=0;i<listDate.size();i++){
            jComboBoxDate.addItem(listDate.get(i));
   
        }
    }
     
    
    public void showPanel(int id) throws Exception {
        //jPanelListFood.removeNotify();
        jPanelListFood.removeAll();
        jPanelListFood.revalidate();
        jPanelListFood.repaint();
        ProductDao dao=new ProductDao();
        ArrayList<Product> list= dao.getAllRows(id);
        JLabel[] labels=new JLabel[list.size()];
        //JPanel panel = new JPanel(new GridBagLayout());
        GridBagLayout layout=new GridBagLayout();
        jPanelListFood.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(2,10,0,0);
        // panel.add(new JButton("Button 1"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
      
        JButton[] button = new JButton[list.size()];
        JLabel[] label = new JLabel[list.size()];
       for (int i=0;i<list.size();i++) {
           if(i % 7 ==0 ){
                gbc.gridx=0;
               gbc.gridy+=4;
           }
           //image
//             File file=new File("resume_from_db.pdf");
//             FileOutputStream output=new FileOutputStream(file);
//             InputStream imageInputStream=new ByteArrayInputStream(list.get(i).getImage().getBytes(StandardCharsets.UTF_8));
//            byte[] buffer=new byte[1024];
//            while (imageInputStream.read(buffer)>0) {
//                output.write(buffer);
//            }
              
        //   Image image = ImageIO.read(this.getClass().getResource("/img/food.PNG"));
         Image image = ImageIO.read(this.getClass().getResource("/img/hamburger.png"));
           if (id==2) {
               image = ImageIO.read(this.getClass().getResource("/img/drink.PNG"));
           }
                
           
             
           Image imageScaled = image.getScaledInstance(70, 50, Image.SCALE_SMOOTH);
           ImageIcon imageIcon = new ImageIcon(imageScaled);
         //  labels[i] = new JLabel(imageIcon);
             jPanelListFood.add(new JLabel(imageIcon), gbc);
             gbc.gridy++;
            jPanelListFood.add(new JLabel(list.get(i).getName()),gbc);
            gbc.gridy++; // remove the row
            jPanelListFood.add(new JLabel(String.valueOf(list.get(i).getPrice()+".VND")),gbc );
             gbc.gridy++; 
           
             if(list.get(i).getQuantity() <= 0){
                 label[i] = new JLabel("Hết hàng");
                 label[i].setForeground(Color.red);
                 jPanelListFood.add(label[i],gbc );
                 gbc.gridy--;
                 gbc.gridy--;
                 gbc.gridy--;
                 gbc.gridx++;
             }
             else{
                 button[i] = new JButton("Add Cart");
                 button[i].setForeground(Color.black);
                 button[i].setBackground(new Color(255, 194, 194));
                 button[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                 jPanelListFood.add(button[i], gbc);
                 gbc.gridy--; // remove the row
                 gbc.gridy--;
                 gbc.gridy--;
                 gbc.gridx++; // move to next column
                 //image
                 button[i].addActionListener(new ClickAddCart(new Product(list.get(i).getId(),
                         list.get(i).getName(), list.get(i).getCategoryID(), 1, list.get(i).getPrice()), jTableListCart, lbtotal,list.get(i).getQuantity()));
                 
             }
           
        
        }
      
         
    }
    //add event
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        bg = new javax.swing.JPanel();
        sidepane = new javax.swing.JPanel();
        btn_home = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_productmanagement = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_nhapxuat = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_administration = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_revenue = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        JPanelHome = new javax.swing.JPanel();
        JPanelCart = new javax.swing.JPanel();
        lbnameProduct = new javax.swing.JLabel();
        lbtotal = new javax.swing.JLabel();
        btn_updatePayment = new keeptoo.KButton();
        jComboBoxCategoty = new javax.swing.JComboBox<>();
        lbpri1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableListCart = new javax.swing.JTable();
        btn_updatePayment1 = new keeptoo.KButton();
        refreshbtn = new javax.swing.JButton();
        jPanelListFood = new javax.swing.JPanel();
        jPanelUser = new javax.swing.JPanel();
        btn_addUser = new keeptoo.KButton();
        btn_delUser = new keeptoo.KButton();
        btn_updateUser = new keeptoo.KButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBLUser = new javax.swing.JTable();
        btn_updateUser1 = new keeptoo.KButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanelRevenue = new javax.swing.JPanel();
        jPanelStatistical = new javax.swing.JPanel();
        jTextFieldRevenue = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxDate = new javax.swing.JComboBox<>();
        btn_refresh = new keeptoo.KButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TBLHistory = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        btn_printRevenue = new keeptoo.KButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanelProduct = new javax.swing.JPanel();
        btn_addProduct = new keeptoo.KButton();
        btn_delProduct = new keeptoo.KButton();
        btn_updateProduct = new keeptoo.KButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TBLProduct = new javax.swing.JTable();
        btn_findProduct = new keeptoo.KButton();
        jTextFieldKey = new javax.swing.JTextField();
        jComboBoxCategoty1 = new javax.swing.JComboBox<>();
        btn_updateUser2 = new keeptoo.KButton();
        jPanelWarehouse = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablenhap = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablexuat = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        innhapbtn = new javax.swing.JButton();
        inxuatbtn = new javax.swing.JButton();
        refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidepane.setBackground(new java.awt.Color(192, 246, 243));
        sidepane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_home.setBackground(new java.awt.Color(255, 255, 204));
        btn_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_homeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_homeMousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_home_page_100px_1.png")));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("HOME");

        javax.swing.GroupLayout btn_homeLayout = new javax.swing.GroupLayout(btn_home);
        btn_home.setLayout(btn_homeLayout);
        btn_homeLayout.setHorizontalGroup(
            btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_homeLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        btn_homeLayout.setVerticalGroup(
            btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_homeLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidepane.add(btn_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 250, 90));

        btn_productmanagement.setBackground(new java.awt.Color(255, 255, 204));
        btn_productmanagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_productmanagementMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_productmanagementMousePressed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_used_product_100px.png")));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("PRODUCT MNG");

        javax.swing.GroupLayout btn_productmanagementLayout = new javax.swing.GroupLayout(btn_productmanagement);
        btn_productmanagement.setLayout(btn_productmanagementLayout);
        btn_productmanagementLayout.setHorizontalGroup(
            btn_productmanagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_productmanagementLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        btn_productmanagementLayout.setVerticalGroup(
            btn_productmanagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_productmanagementLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_productmanagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidepane.add(btn_productmanagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 250, 90));

        btn_nhapxuat.setBackground(new java.awt.Color(255, 255, 204));
        btn_nhapxuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_nhapxuatMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_nhapxuatMousePressed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_100px.png")));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("WAREHOUSE MNG");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_nhapxuatLayout = new javax.swing.GroupLayout(btn_nhapxuat);
        btn_nhapxuat.setLayout(btn_nhapxuatLayout);
        btn_nhapxuatLayout.setHorizontalGroup(
            btn_nhapxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_nhapxuatLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
        );
        btn_nhapxuatLayout.setVerticalGroup(
            btn_nhapxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_nhapxuatLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_nhapxuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidepane.add(btn_nhapxuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 250, 90));

        btn_administration.setBackground(new java.awt.Color(255, 255, 204));
        btn_administration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_administrationMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_administrationMousePressed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_workspace_one_100px.png")));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("ADMINISTRATION");

        javax.swing.GroupLayout btn_administrationLayout = new javax.swing.GroupLayout(btn_administration);
        btn_administration.setLayout(btn_administrationLayout);
        btn_administrationLayout.setHorizontalGroup(
            btn_administrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_administrationLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(464, 464, 464))
        );
        btn_administrationLayout.setVerticalGroup(
            btn_administrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_administrationLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_administrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidepane.add(btn_administration, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 90));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_food_100px.png")));
        sidepane.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 250, 60));

        btn_revenue.setBackground(new java.awt.Color(255, 255, 204));
        btn_revenue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_revenueMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_revenueMousePressed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_document_100px.png")));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("REVENUE");

        javax.swing.GroupLayout btn_revenueLayout = new javax.swing.GroupLayout(btn_revenue);
        btn_revenue.setLayout(btn_revenueLayout);
        btn_revenueLayout.setHorizontalGroup(
            btn_revenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_revenueLayout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
        );
        btn_revenueLayout.setVerticalGroup(
            btn_revenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_revenueLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(btn_revenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidepane.add(btn_revenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 250, 110));

        bg.add(sidepane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 550));

        JPanelHome.setBackground(new java.awt.Color(192, 246, 243));
        JPanelHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        JPanelCart.setBackground(new java.awt.Color(192, 246, 243));
        JPanelCart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbnameProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lbtotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbtotal.setText("0");

        btn_updatePayment.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_updatePayment.setText("Payment");
        btn_updatePayment.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_updatePayment.setkBackGroundColor(new java.awt.Color(255, 255, 255));
        btn_updatePayment.setkEndColor(new java.awt.Color(255, 153, 153));
        btn_updatePayment.setkForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment.setkHoverColor(new java.awt.Color(0, 0, 0));
        btn_updatePayment.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment.setkHoverStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment.setkStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatePaymentActionPerformed(evt);
            }
        });

        jComboBoxCategoty.setBackground(new java.awt.Color(255, 255, 102));
        jComboBoxCategoty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategotyActionPerformed(evt);
            }
        });

        lbpri1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbpri1.setText("Price Total :");

        jTableListCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name", "Quantity", "Price"
            }
        )
        {
            public boolean isCellEditable(int row,int column){
                return false;
            }
        }
    );
    jTableListCart.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jTableListCart.setDoubleBuffered(true);
    jTableListCart.setGridColor(new java.awt.Color(0, 0, 0));
    jTableListCart.setRowHeight(14);
    jTableListCart.setSelectionBackground(new java.awt.Color(153, 153, 0));
    jTableListCart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane5.setViewportView(jTableListCart);

    btn_updatePayment1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    btn_updatePayment1.setText("Delete Item");
    btn_updatePayment1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_updatePayment1.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_updatePayment1.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_updatePayment1.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_updatePayment1.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_updatePayment1.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_updatePayment1.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_updatePayment1.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_updatePayment1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_updatePayment1ActionPerformed(evt);
        }
    });

    refreshbtn.setText("Refresh");
    refreshbtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            refreshbtnActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout JPanelCartLayout = new javax.swing.GroupLayout(JPanelCart);
    JPanelCart.setLayout(JPanelCartLayout);
    JPanelCartLayout.setHorizontalGroup(
        JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(JPanelCartLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(JPanelCartLayout.createSequentialGroup()
                    .addGroup(JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(JPanelCartLayout.createSequentialGroup()
                            .addComponent(jComboBoxCategoty, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(51, 51, 51)
                            .addComponent(btn_updatePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JPanelCartLayout.createSequentialGroup()
                            .addComponent(lbpri1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbnameProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(JPanelCartLayout.createSequentialGroup()
                    .addComponent(btn_updatePayment1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(119, 119, 119)
                    .addComponent(refreshbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(21, 21, 21))))
    );
    JPanelCartLayout.setVerticalGroup(
        JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelCartLayout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelCartLayout.createSequentialGroup()
                    .addGroup(JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBoxCategoty, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(btn_updatePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelCartLayout.createSequentialGroup()
                            .addComponent(btn_updatePayment1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(3, 3, 3))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelCartLayout.createSequentialGroup()
                            .addComponent(refreshbtn)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbnameProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbpri1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(34, 34, 34))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelCartLayout.createSequentialGroup()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26))))
    );

    jPanelListFood.setBackground(new java.awt.Color(192, 246, 243));
    jPanelListFood.setAutoscrolls(true);
    jPanelListFood.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jPanelListFood.setDoubleBuffered(false);
    jPanelListFood.setPreferredSize(new java.awt.Dimension(0, 0));

    javax.swing.GroupLayout jPanelListFoodLayout = new javax.swing.GroupLayout(jPanelListFood);
    jPanelListFood.setLayout(jPanelListFoodLayout);
    jPanelListFoodLayout.setHorizontalGroup(
        jPanelListFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );
    jPanelListFoodLayout.setVerticalGroup(
        jPanelListFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout JPanelHomeLayout = new javax.swing.GroupLayout(JPanelHome);
    JPanelHome.setLayout(JPanelHomeLayout);
    JPanelHomeLayout.setHorizontalGroup(
        JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(JPanelHomeLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(JPanelCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addComponent(jPanelListFood, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
    );
    JPanelHomeLayout.setVerticalGroup(
        JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelHomeLayout.createSequentialGroup()
            .addComponent(jPanelListFood, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(JPanelCart, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(26, 26, 26))
    );

    bg.add(JPanelHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 690, 470));

    jPanelUser.setBackground(new java.awt.Color(192, 246, 243));
    jPanelUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jPanelUser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jPanelUser.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

    btn_addUser.setText("ADD");
    btn_addUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_addUser.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_addUser.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_addUser.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_addUser.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_addUser.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_addUser.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_addUser.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_addUser.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_addUserActionPerformed(evt);
        }
    });

    btn_delUser.setText("DELETE");
    btn_delUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_delUser.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_delUser.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_delUser.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_delUser.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_delUser.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_delUser.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_delUser.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_delUser.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_delUserActionPerformed(evt);
        }
    });

    btn_updateUser.setText("EDIT");
    btn_updateUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_updateUser.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_updateUser.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_updateUser.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_updateUser.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_updateUser.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_updateUser.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_updateUser.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_updateUser.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_updateUserActionPerformed(evt);
        }
    });

    TBLUser.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null}
        },
        new String [] {
            "id", "Name", "Username", "Password", "Sex", "Role"
        }
    ));
    TBLUser.setGridColor(new java.awt.Color(0, 0, 0));
    TBLUser.setInheritsPopupMenu(true);
    TBLUser.setRowHeight(20);
    TBLUser.setSelectionBackground(new java.awt.Color(204, 204, 0));
    TBLUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    TBLUser.setShowGrid(true);
    jScrollPane1.setViewportView(TBLUser);

    btn_updateUser1.setText("REFRESH");
    btn_updateUser1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_updateUser1.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_updateUser1.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_updateUser1.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_updateUser1.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_updateUser1.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_updateUser1.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_updateUser1.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_updateUser1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_updateUser1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanelUserLayout = new javax.swing.GroupLayout(jPanelUser);
    jPanelUser.setLayout(jPanelUserLayout);
    jPanelUserLayout.setHorizontalGroup(
        jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelUserLayout.createSequentialGroup()
            .addGap(27, 27, 27)
            .addComponent(btn_addUser, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(74, 74, 74)
            .addComponent(btn_updateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_delUser, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(84, 84, 84)
            .addComponent(btn_updateUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(51, 51, 51))
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelUserLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanelUserLayout.setVerticalGroup(
        jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelUserLayout.createSequentialGroup()
            .addGap(22, 22, 22)
            .addGroup(jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_addUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_updateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_delUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_updateUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    btn_updateUser.getAccessibleContext().setAccessibleName("");

    bg.add(jPanelUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, -1, 471));

    jPanel1.setBackground(new java.awt.Color(192, 246, 243));

    jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_love_100px.PNG")));

    jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    jLabel11.setText("X");
    jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            jLabel11MousePressed(evt);
        }
    });

    jButton1.setBackground(new java.awt.Color(255, 255, 153));
    jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jButton1.setText("Logout");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(401, 401, 401)
            .addComponent(jLabel17)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1)
            .addGap(49, 49, 49)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(492, 492, 492))
    );

    bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 690, 80));

    jPanelRevenue.setBackground(new java.awt.Color(0, 0, 255));
    jPanelRevenue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jPanelRevenue.setPreferredSize(new java.awt.Dimension(640, 440));

    jPanelStatistical.setBackground(new java.awt.Color(192, 246, 243));

    jTextFieldRevenue.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldRevenueActionPerformed(evt);
        }
    });

    jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    jLabel15.setText("Revenue");

    jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxDateActionPerformed(evt);
        }
    });

    btn_refresh.setText("Refresh");
    btn_refresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_refresh.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_refresh.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_refresh.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_refresh.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_refresh.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_refresh.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_refresh.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_refresh.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_refreshActionPerformed(evt);
        }
    });

    TBLHistory.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "ID Bill", "Time", "Date", "Total"
        }
    ));
    TBLHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    TBLHistory.setRowHeight(20);
    TBLHistory.setSelectionBackground(new java.awt.Color(255, 204, 102));
    TBLHistory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    TBLHistory.setShowVerticalLines(false);
    TBLHistory.getTableHeader().setReorderingAllowed(false);
    TBLHistory.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            TBLHistoryMouseClicked(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            TBLHistoryMousePressed(evt);
        }
    });
    jScrollPane3.setViewportView(TBLHistory);

    jLabel19.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
    jLabel19.setText("History");

    btn_printRevenue.setText("Print");
    btn_printRevenue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_printRevenue.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_printRevenue.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_printRevenue.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_printRevenue.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_printRevenue.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_printRevenue.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_printRevenue.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_printRevenue.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_printRevenueActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanelStatisticalLayout = new javax.swing.GroupLayout(jPanelStatistical);
    jPanelStatistical.setLayout(jPanelStatisticalLayout);
    jPanelStatisticalLayout.setHorizontalGroup(
        jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStatisticalLayout.createSequentialGroup()
            .addGroup(jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanelStatisticalLayout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(jComboBoxDate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanelStatisticalLayout.createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addGroup(jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelStatisticalLayout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_printRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelStatisticalLayout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addGap(18, 18, 18)
                            .addComponent(jTextFieldRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGap(18, 18, 18)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStatisticalLayout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(38, 38, 38))
    );
    jPanelStatisticalLayout.setVerticalGroup(
        jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelStatisticalLayout.createSequentialGroup()
            .addGap(28, 28, 28)
            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelStatisticalLayout.createSequentialGroup()
                    .addComponent(jComboBoxDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                    .addGroup(jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTextFieldRevenue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(83, 83, 83)
                    .addGroup(jPanelStatisticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_printRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(40, 40, 40))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanelRevenueLayout = new javax.swing.GroupLayout(jPanelRevenue);
    jPanelRevenue.setLayout(jPanelRevenueLayout);
    jPanelRevenueLayout.setHorizontalGroup(
        jPanelRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanelStatistical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    jPanelRevenueLayout.setVerticalGroup(
        jPanelRevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanelStatistical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    bg.add(jPanelRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 690, 470));
    bg.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    jPanelProduct.setBackground(new java.awt.Color(192, 246, 243));
    jPanelProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    btn_addProduct.setText("ADD");
    btn_addProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_addProduct.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_addProduct.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_addProduct.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_addProduct.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_addProduct.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_addProduct.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_addProduct.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_addProduct.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_addProductActionPerformed(evt);
        }
    });

    btn_delProduct.setText("DELETE");
    btn_delProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_delProduct.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_delProduct.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_delProduct.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_delProduct.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_delProduct.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_delProduct.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_delProduct.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_delProduct.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_delProductActionPerformed(evt);
        }
    });

    btn_updateProduct.setText("EDIT");
    btn_updateProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_updateProduct.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_updateProduct.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_updateProduct.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_updateProduct.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_updateProduct.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_updateProduct.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_updateProduct.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_updateProduct.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_updateProductActionPerformed(evt);
        }
    });

    TBLProduct.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    TBLProduct.setRowHeight(20);
    TBLProduct.setSelectionBackground(new java.awt.Color(255, 204, 102));
    TBLProduct.setShowVerticalLines(false);
    TBLProduct.getTableHeader().setReorderingAllowed(false);
    jScrollPane2.setViewportView(TBLProduct);

    btn_findProduct.setText("FIND");
    btn_findProduct.setToolTipText("");
    btn_findProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_findProduct.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_findProduct.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_findProduct.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_findProduct.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_findProduct.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_findProduct.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_findProduct.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_findProduct.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_findProductActionPerformed(evt);
        }
    });

    jTextFieldKey.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jTextFieldKeyKeyPressed(evt);
        }
    });

    jComboBoxCategoty1.setBackground(new java.awt.Color(255, 255, 102));
    jComboBoxCategoty1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxCategoty1ActionPerformed(evt);
        }
    });

    btn_updateUser2.setText("REFRESH");
    btn_updateUser2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    btn_updateUser2.setkBackGroundColor(new java.awt.Color(255, 255, 255));
    btn_updateUser2.setkEndColor(new java.awt.Color(255, 153, 153));
    btn_updateUser2.setkForeGround(new java.awt.Color(0, 0, 0));
    btn_updateUser2.setkHoverColor(new java.awt.Color(0, 0, 0));
    btn_updateUser2.setkHoverForeGround(new java.awt.Color(0, 0, 0));
    btn_updateUser2.setkHoverStartColor(new java.awt.Color(255, 255, 204));
    btn_updateUser2.setkStartColor(new java.awt.Color(255, 255, 204));
    btn_updateUser2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_updateUser2ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanelProductLayout = new javax.swing.GroupLayout(jPanelProduct);
    jPanelProduct.setLayout(jPanelProductLayout);
    jPanelProductLayout.setHorizontalGroup(
        jPanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelProductLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2)
                .addGroup(jPanelProductLayout.createSequentialGroup()
                    .addComponent(btn_addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)
                    .addComponent(btn_updateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btn_delProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btn_updateUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jComboBoxCategoty1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btn_findProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jTextFieldKey, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
            .addContainerGap())
    );
    jPanelProductLayout.setVerticalGroup(
        jPanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelProductLayout.createSequentialGroup()
            .addContainerGap(36, Short.MAX_VALUE)
            .addGroup(jPanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTextFieldKey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_updateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_findProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCategoty1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(btn_updateUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(18, 18, 18)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(43, 43, 43))
    );

    bg.add(jPanelProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, -1, 470));

    jPanelWarehouse.setBackground(new java.awt.Color(192, 246, 243));
    jPanelWarehouse.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jPanelWarehouse.setPreferredSize(new java.awt.Dimension(640, 440));

    tablenhap.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
        }
    ));
    tablenhap.setShowVerticalLines(false);
    jScrollPane8.setViewportView(tablenhap);

    jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel9.setText("NHẬP KHO");

    tablexuat.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
        },
        new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
        }
    ));
    tablexuat.setShowVerticalLines(false);
    jScrollPane9.setViewportView(tablexuat);

    jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel10.setText("XUẤT KHO");

    innhapbtn.setText("In phiếu nhập kho");
    innhapbtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            innhapbtnActionPerformed(evt);
        }
    });

    inxuatbtn.setText("In phiếu xuất kho");
    inxuatbtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            inxuatbtnActionPerformed(evt);
        }
    });

    refresh.setText("Refresh");
    refresh.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            refreshActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanelWarehouseLayout = new javax.swing.GroupLayout(jPanelWarehouse);
    jPanelWarehouse.setLayout(jPanelWarehouseLayout);
    jPanelWarehouseLayout.setHorizontalGroup(
        jPanelWarehouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanelWarehouseLayout.createSequentialGroup()
            .addGap(190, 190, 190)
            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanelWarehouseLayout.createSequentialGroup()
            .addGroup(jPanelWarehouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelWarehouseLayout.createSequentialGroup()
                    .addGap(64, 64, 64)
                    .addComponent(innhapbtn)
                    .addGap(52, 52, 52)
                    .addComponent(inxuatbtn)
                    .addGap(99, 99, 99)
                    .addComponent(refresh))
                .addGroup(jPanelWarehouseLayout.createSequentialGroup()
                    .addGap(192, 192, 192)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelWarehouseLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelWarehouseLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(114, Short.MAX_VALUE))
    );
    jPanelWarehouseLayout.setVerticalGroup(
        jPanelWarehouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelWarehouseLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel10)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanelWarehouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(innhapbtn)
                .addComponent(inxuatbtn)
                .addComponent(refresh))
            .addContainerGap(38, Short.MAX_VALUE))
    );

    bg.add(jPanelWarehouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 690, 470));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMousePressed
        // TODO add your handling code here:
        setColor(btn_home);
        resetColor(btn_administration);
        resetColor(btn_productmanagement);
        resetColor(btn_revenue);
        resetColor(btn_nhapxuat);
         
    }//GEN-LAST:event_btn_homeMousePressed

    private void btn_productmanagementMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_productmanagementMousePressed
        // TODO add your handling code here:
       setColor(btn_productmanagement);
        resetColor(btn_administration);
        resetColor(btn_home);
        resetColor(btn_revenue);
        resetColor(btn_nhapxuat);
    }//GEN-LAST:event_btn_productmanagementMousePressed

    private void btn_nhapxuatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nhapxuatMousePressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_btn_nhapxuatMousePressed

    private void btn_administrationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_administrationMousePressed
        // TODO add your handling code here:
         if (Role.Role==2) { 
            setColor(btn_administration);
            resetColor(btn_productmanagement);
            resetColor(btn_home);
            resetColor(btn_revenue);
            resetColor(btn_nhapxuat);
         }else{
             resetColor(btn_administration);
         }
    }//GEN-LAST:event_btn_administrationMousePressed

    private void btn_revenueMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_revenueMousePressed
        // TODO add your handling code here:
             
            setColor(btn_revenue);
            resetColor(btn_administration);
            resetColor(btn_home);
            resetColor(btn_productmanagement);
            resetColor(btn_nhapxuat);
        
       
    }//GEN-LAST:event_btn_revenueMousePressed

    private void btn_updateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateUserActionPerformed
        int row=TBLUser.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(Home.this, "Bạn Chưa Chọn Hàng Cần Cập Nhật !!!");
        }else{
            int idUser= Integer.parseInt(String.valueOf(TBLUser.getValueAt(row, 0)));
           new UpdateUser(idUser).setVisible(true);
          // this.dispose();
        }
    }//GEN-LAST:event_btn_updateUserActionPerformed

    private void btn_delUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delUserActionPerformed
        int row=TBLUser.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(Home.this, "Bạn Chưa Chọn Hàng Cần Xóa !!!");
        }else{
            int a = JOptionPane.showConfirmDialog(Home.this, "Bạn Có Chắc Xóa ","Xác nhận xoá",JOptionPane.YES_NO_OPTION);
            if(a == JOptionPane.YES_OPTION){
                        int idUser= Integer.parseInt(String.valueOf(TBLUser.getValueAt(row, 0)));
                     UserDao dao=new UserDao();
                     if (dao.deleteUser(idUser)>0) {
         //                DefaultTableModel tableModel = (DefaultTableModel) TBLUser.getModel();
         //                tableModel.setRowCount(0);
                        JOptionPane.showMessageDialog(Home.this, "Xoá thành công!");
                         try {
                             loadListUser();
                         } catch (Exception ex) {
                             Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     } 
            }
            
        }
    }//GEN-LAST:event_btn_delUserActionPerformed

    private void btn_addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addUserActionPerformed
        new AddUser().setVisible(true);
    }//GEN-LAST:event_btn_addUserActionPerformed

    private void btn_addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addProductActionPerformed

        //SỬA HÀM NÀY
        if(Role.Role == 1){
           btn_addProduct.setEnabled(false);
        }
        else{
            new AddProduct().setVisible(true);
        }
        
    }//GEN-LAST:event_btn_addProductActionPerformed

    private void btn_delProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delProductActionPerformed
        // TODO add your handling code here:
        if(Role.Role == 1){
            btn_delProduct.setEnabled(false);
        }
        else{
            int row=TBLProduct.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(Home.this, "Bạn Chưa Chọn Hàng Cần Xóa !!!");
        }else{
                int a = JOptionPane.showConfirmDialog(Home.this, "Bạn Có Chắc Xóa ","Xác Nhận Xoá",JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.YES_OPTION){
                            int id= Integer.parseInt(String.valueOf(TBLProduct.getValueAt(row, 0)));
                        ProductDao dao=new ProductDao();
                        if (dao.deleteProduct(id)>0) {
                                //DefaultTableModel tableModel = (DefaultTableModel) TBLUser.getModel();
                                //tableModel.setRowCount(0);
                            try {
                                loadListProduct();
                            } catch (Exception ex) {
                                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                }

            }
        }
    }//GEN-LAST:event_btn_delProductActionPerformed

    private void btn_updateProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateProductActionPerformed
        if(Role.Role == 1){
            btn_updateProduct.setEnabled(false);
        }
        else {
                int row=TBLProduct.getSelectedRow();
             if(row == -1){
                 JOptionPane.showMessageDialog(Home.this, "Bạn Chưa Chọn Hàng Cần Cập Nhật !!!");
             }else{
                 int id= Integer.parseInt(String.valueOf(TBLProduct.getValueAt(row, 0)));
                new UpdateProduct(id).setVisible(true);
               // this.dispose();
             } 
        }
        
    }//GEN-LAST:event_btn_updateProductActionPerformed

    private void btn_findProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_findProductActionPerformed
        // TODO add your handling code here:
        String keyString=jTextFieldKey.getText().trim();
        if(keyString.equals("")){
            JOptionPane.showMessageDialog(Home.this, "Hãy nhập món cần tìm");
            jTextFieldKey.requestFocus(true);
        }else {
                    TBLProduct.removeAll();
             TBLProduct.revalidate();
             TBLProduct.repaint();
             DefaultTableModel defaultTableModel=new DefaultTableModel();
             TBLProduct.setModel(defaultTableModel);           //setting lại table TBLProduct
            ProductDao dao=new ProductDao();
            try {
                ArrayList<Product> products=dao.searchProduct(keyString);
                if(!products.isEmpty()){
                 defaultTableModel.addColumn("id");
                     defaultTableModel.addColumn("name");
                     defaultTableModel.addColumn("Category");
                     defaultTableModel.addColumn("quantity");
                     defaultTableModel.addColumn("price");
                     for(Product product:products) {

                             String name=product.getName();
                             int cat=product.getCategoryID();
                             int quantity=product.getQuantity();
                             int price=product.getPrice();
                             String categoryString=null;
                             if (product.getCategoryID()==1) {
                                categoryString="Food";
                             }else{
                              categoryString="Drink";
                             }
                              defaultTableModel.addRow(new Object[] { product.getId(),name,categoryString,quantity,price});
                         }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Không tìm thấy món ăn!");
                    try {
                        loadListProduct();
                        
                    }
                    catch(Exception ex){
                        System.out.println(ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        
               
    }//GEN-LAST:event_btn_findProductActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        try {
            jComboBoxDate.setSelectedIndex(0);
            LoadListBill();
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseClicked
        // TODO add your handling code here:
        setVisiblePanel(JPanelHome);
        try {
            showPanel(1);
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_homeMouseClicked

    private void btn_productmanagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_productmanagementMouseClicked
        // TODO add your handling code here:
        setVisiblePanel(jPanelProduct);
       
    }//GEN-LAST:event_btn_productmanagementMouseClicked

    private void btn_administrationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_administrationMouseClicked
        // TODO add your handling code here:
        if (Role.Role==1) {
            JOptionPane.showMessageDialog(Home.this, "Bạn Không Đủ Tư Cách Để Xem Phần Này !!!");
            
        }else{
             setVisiblePanel(jPanelUser);
        }
       
    }//GEN-LAST:event_btn_administrationMouseClicked

    private void btn_revenueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_revenueMouseClicked
        // TODO add your handling code here:
        setVisiblePanel(jPanelRevenue);
    }//GEN-LAST:event_btn_revenueMouseClicked

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        // TODO add your handling code here:
         System.exit(0);
    }//GEN-LAST:event_jLabel11MousePressed

    private void btn_updatePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePaymentActionPerformed
        // TODO add your handling code here:
        int rows=jTableListCart.getRowCount();
        if(rows>0){
            new Payment(lbtotal,jTableListCart).setVisible(true);
        }
        if(rows == 0){
            JOptionPane.showMessageDialog(null, "Không có món để thanh toán,hãy chọn món!");
        }
    }//GEN-LAST:event_btn_updatePaymentActionPerformed

    private void btn_updatePayment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePayment1ActionPerformed
        int rowSelect=jTableListCart.getSelectedRow();
        DefaultTableModel model=new DefaultTableModel();
       model=(DefaultTableModel) jTableListCart.getModel();
       
        if(rowSelect < 0){
            JOptionPane.showMessageDialog(Home.this, "Bạn Chưa Chọn Item Cần Xóa !!!");
        }else{
             model.removeRow(rowSelect);
              int rows = jTableListCart.getRowCount();
            double total=0;
            for (int row = 0; row < rows; row++) {
                total+=Double.parseDouble(String.valueOf(jTableListCart.getValueAt(row, 3)) ) ;
                
            }
            DecimalFormat formatter = new DecimalFormat("###,###,###");


              lbtotal.setText(formatter.format(total)+" VNĐ");
        }
    }//GEN-LAST:event_btn_updatePayment1ActionPerformed

    private void btn_updateUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateUser1ActionPerformed
         TBLUser.removeAll();
        TBLUser.revalidate();
        TBLUser.repaint();
        try {
            loadListUser();
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_updateUser1ActionPerformed

    private void jComboBoxCategotyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategotyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCategotyActionPerformed

    private void jComboBoxCategoty1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoty1ActionPerformed
        
    }//GEN-LAST:event_jComboBoxCategoty1ActionPerformed

    private void btn_updateUser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateUser2ActionPerformed
        // TODO add your handling code here:
        jTextFieldKey.setText("");
         TBLProduct.removeAll();
        TBLProduct.revalidate();
        TBLProduct.repaint();
        try {
            loadListProduct();
            jComboBoxCategoty1.setSelectedIndex(0);
        } catch (Exception ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_updateUser2ActionPerformed

    private void jTextFieldKeyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldKeyKeyPressed
        // TODO add your handling code here:
        //String key=String.valueOf(evt.getKeyChar());
        //System.out.println(key);
             
    }//GEN-LAST:event_jTextFieldKeyKeyPressed

    private void TBLHistoryMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLHistoryMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBLHistoryMousePressed

    private void TBLHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLHistoryMouseClicked
       
                   int row = TBLHistory.getSelectedRow(); 
                   
                       
                       try {
                           int id=Integer.parseInt(String.valueOf(TBLHistory.getValueAt(row, 0)) ) ;
                           new DetailBill(id).setVisible(true);
                       } catch (Exception ex) {
                           Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                       }
                  
                   
                    
            
        
    }//GEN-LAST:event_TBLHistoryMouseClicked

    private void jTextFieldRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRevenueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRevenueActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new SignUp().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDateActionPerformed

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        // TODO add your handling code here:
        try{
            showPanel(1);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_refreshbtnActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jLabel6MouseClicked

    private void btn_nhapxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nhapxuatMouseClicked
        // TODO add your handling code here:
        setVisiblePanel(jPanelWarehouse);
        setColor(btn_nhapxuat);
        resetColor(btn_administration);
        resetColor(btn_productmanagement);
        resetColor(btn_revenue);
       resetColor(btn_home);
    }//GEN-LAST:event_btn_nhapxuatMouseClicked

    private void innhapbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_innhapbtnActionPerformed
        MessageFormat header = new MessageFormat("Phiếu Nhập Kho");
        MessageFormat footer = new MessageFormat("Page(0, number, intger)");
        try {
            tablenhap.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_innhapbtnActionPerformed

    private void inxuatbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inxuatbtnActionPerformed
        // TODO add your handling code here:
        MessageFormat header = new MessageFormat("Phiếu Xuất Kho");
        MessageFormat footer = new MessageFormat("Page(0, number, intger)");
        try {
            tablexuat.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_inxuatbtnActionPerformed
    /* PRINT HOA DON*/
    /**/
   
    private void btn_printRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printRevenueActionPerformed
        // TODO add your handling code here:
         MessageFormat header = new MessageFormat("Doanh Thu");
        MessageFormat footer = new MessageFormat("Page(0, number, integer)");
        try{
            TBLHistory.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_printRevenueActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        try{
            loadListWarehouseIn();
            loadListWarehouseOut();
        }
        catch(Exception e){
            System.out.println(e);
        }
       
    }//GEN-LAST:event_refreshActionPerformed
    
   
    
    
    
    void resetColor(JPanel panel){
        panel.setBackground(new Color(255,255,204));
}
    void setColor(JPanel panel){
        panel.setBackground(new Color(255,230,180));
    }
    void setVisiblePanel(JPanel panel){
//        JPanelCart.setVisible(false);
//        jPanelStatistical.setVisible(false);
        jPanelUser.setVisible(false);
        JPanelHome.setVisible(false);
        jPanelProduct.setVisible(false);
        jPanelRevenue.setVisible(false);
        jPanelWarehouse.setVisible(false);
        panel.setVisible(true);
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Home().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelCart;
    private javax.swing.JPanel JPanelHome;
    private javax.swing.JTable TBLHistory;
    private javax.swing.JTable TBLProduct;
    private javax.swing.JTable TBLUser;
    private javax.swing.JPanel bg;
    private keeptoo.KButton btn_addProduct;
    private keeptoo.KButton btn_addUser;
    private javax.swing.JPanel btn_administration;
    private keeptoo.KButton btn_delProduct;
    private keeptoo.KButton btn_delUser;
    private keeptoo.KButton btn_findProduct;
    private javax.swing.JPanel btn_home;
    private javax.swing.JPanel btn_nhapxuat;
    private keeptoo.KButton btn_printRevenue;
    private javax.swing.JPanel btn_productmanagement;
    private keeptoo.KButton btn_refresh;
    private javax.swing.JPanel btn_revenue;
    private keeptoo.KButton btn_updatePayment;
    private keeptoo.KButton btn_updatePayment1;
    private keeptoo.KButton btn_updateProduct;
    private keeptoo.KButton btn_updateUser;
    private keeptoo.KButton btn_updateUser1;
    private keeptoo.KButton btn_updateUser2;
    private javax.swing.JButton innhapbtn;
    private javax.swing.JButton inxuatbtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxCategoty;
    private javax.swing.JComboBox<String> jComboBoxCategoty1;
    private javax.swing.JComboBox<String> jComboBoxDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelListFood;
    private javax.swing.JPanel jPanelProduct;
    private javax.swing.JPanel jPanelRevenue;
    private javax.swing.JPanel jPanelStatistical;
    private javax.swing.JPanel jPanelUser;
    private javax.swing.JPanel jPanelWarehouse;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTableListCart;
    private javax.swing.JTextField jTextFieldKey;
    private javax.swing.JTextField jTextFieldRevenue;
    private javax.swing.JLabel lbnameProduct;
    private javax.swing.JLabel lbpri1;
    private javax.swing.JLabel lbtotal;
    private javax.swing.JButton refresh;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JPanel sidepane;
    private javax.swing.JTable tablenhap;
    private javax.swing.JTable tablexuat;
    // End of variables declaration//GEN-END:variables

    
}
