public class N196_WorkerForm extends javax.swing.JFrame {
    DefaultTableModel model;
    private String tabel_Click;
    private Object posisi;
    
    /**
     * Creates new form WorkerForm
     */
    public N196_WorkerForm() {
        initComponents();
        txtGaji.setEditable(false);
        this.showData();
    }
    
    public N196_WorkerForm(String username) {
        initComponents();
        txtGaji.setEditable(false);
        this.showData();
    }
    
   
    public void showData()
    {
        model = (DefaultTableModel)jTable1.getModel();
        jTable1.setModel(model);
        DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
        while (dtm.getRowCount() > 0)
        {
            dtm.removeRow(0);
        }
        
        try 
        {
            Connection conn = N196_KoneksiDB.getKoneksi();
            Statement state = conn.createStatement();
            
            String query = "SELECT * FROM workers";
            ResultSet result = state.executeQuery(query);
            
            while (result.next()) {
                Object [] obj = new Object[5];
                obj[0] = result.getString("name_worker");
                obj[1] = result.getString("no_worker");
                obj[2] = result.getString("position");
                obj[3] = result.getString("salary");
                obj[4] = result.getString("id_worker");

                model.addRow(obj);
           
            }
            result.close();
            state.close();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            tabel_Click = "";
            jTable1.getColumnModel().getColumn(4).setMinWidth(0);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(4).setWidth(0);
        }
   }

    

    public void fieldData() throws N196_NoMustbe10Digit, N196_NamaMustBeFilledException
    {
        int no = 0;
        String nama;
        int lengthOfInt;
        try {
           no =  Integer.parseInt(txtNo.getText());
        } catch (NumberFormatException e) {
            throw new N196_NoMustbe10Digit();
        }
        
        try {
            lengthOfInt = String.valueOf(no).length();
        } catch (NumberFormatException e) {
            throw new N196_NoMustbe10Digit();
        } 
        
        try {
            nama = String.valueOf(txtNama.getText().equals(""));
        } catch (NumberFormatException e) {
            throw new N196_NamaMustBeFilledException();
        }
        
        
        
         if (no < 0) 
        {
            throw new N196_NoMustbe10Digit();
        } 
        else if (lengthOfInt !=10)
        {
            throw new N196_NoMustbe10Digit();
        } 
         else if (txtNama.getText().equals(""))
        {
            throw new N196_NamaMustBeFilledException();
        } 
              
    }
    

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        System.exit(0); 
          
    }                                    

    private void btnInsertMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        
        try 
        {
            this.fieldData();
        } 
        catch (N196_NoMustbe10Digit e) 
        {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (N196_NamaMustBeFilledException e) 
        {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } 
            
       
        
        try 
        {
            Connection conn = N196_KoneksiDB.getKoneksi();
            
              String query = "INSERT INTO workers (name_worker, no_worker, position, salary) " + "VALUES(?,?,?,?)";
            
            PreparedStatement pst = conn.prepareCall(query);
            
            pst.setString(1, txtNama.getText());
            pst.setString(2, txtNo.getText());
            pst.setString(3, cmbPosisi.getSelectedItem().toString());
            pst.setString(4, txtGaji.getText());
            
//            p.executeUpdate();
            
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0)
            {
                JOptionPane.showMessageDialog(this, "Data was Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            pst.close();
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Data was Failed to Generate...", "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            this.showData();
        } 
    }                                      

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
         try 
        {
//          
                String name_worker = txtNama.getText();
                String no_worker = txtNo.getText();
                String position = cmbPosisi.getSelectedItem().toString();
                String salary = txtGaji.getText();

                com.mysql.jdbc.Statement statement = (com.mysql.jdbc.Statement) N196_KoneksiDB.getKoneksi().createStatement();
                statement.executeUpdate ("UPDATE workers SET no_worker='"+no_worker+"'," + "position='"+position+"',"
                                        + "salary='"+salary+"'"+ "WHERE name_worker='"+name_worker+"'");
                statement.close();
//            
                {
            
                    JOptionPane.showMessageDialog(this, "An existing user was updated successfully!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                    
                }
               
        } 
         catch (SQLException e) 
        {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Update Failed...", "Error", JOptionPane.ERROR_MESSAGE);
            
        }
        finally
        {
            this.showData();
        }                             

    }                                      

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
         try 
        {
            String no_worker = txtNo.getText();
            //Connection conn = N196_KoneksiDB.getKoneksi();
            
            com.mysql.jdbc.Statement statement = (com.mysql.jdbc.Statement) N196_KoneksiDB.getKoneksi().createStatement();
            statement.executeUpdate("DELETE FROM workers WHERE no_worker = '" + no_worker + "'");   
                {
                    JOptionPane.showMessageDialog(this, "An existing user was deleted successfully!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                }
            
                refresh();
        } catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Delete Failed...", "Error", JOptionPane.ERROR_MESSAGE);
            
        }
        finally
        {
            this.showData();
        }

    }                                      

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        int row =  jTable1.getSelectedRow();

        txtNama.setText(jTable1.getModel().getValueAt(row, 0).toString());
        txtNo.setText(jTable1.getModel().getValueAt(row, 1).toString());
        
        cmbPosisi.setSelectedItem(jTable1.getModel().getValueAt(row, 2).toString());
        
        txtGaji.setText(jTable1.getModel().getValueAt(row, 3).toString());
        
      
    }                                    

    private void cmbPosisiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {                                                       
    }                                                      

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void cmbPosisiMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        String item = (String) cmbPosisi.getSelectedItem();
        
        switch(item){
            case "Senior Programmer": 
                txtGaji.setText("15000000");
                break;
            case "Bussiness Analyst":
                txtGaji.setText("21000000");
                break;
            case "Data Center Officer": 
                txtGaji.setText("18000000");
                break;
            case "Junior Programmer":
                txtGaji.setText("8000000");
                break;
        }        
    }                                  
