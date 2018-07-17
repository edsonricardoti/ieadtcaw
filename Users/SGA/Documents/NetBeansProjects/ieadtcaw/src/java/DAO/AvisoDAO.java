package DAO;

import Modelo.Avisos;
import static Util.HibernateUtil.getSessionFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sun.misc.BASE64Decoder;

public class AvisoDAO {

    private Session session;

    public List<Avisos> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Avisos where Year(dataAviso)=Year(now())").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Avisos> marcadosPreplicar() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Avisos where lido=2").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Avisos> selectSemana(Date dataini, Date datafim) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datafim);
        cal.add(Calendar.DATE, 1);
        Date datafim2 = cal.getTime();
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Avisos where dataAviso >=:dataini and dataAviso <=:datafim2 order by dataAviso")
                    .setDate("dataini", dataini)
                    .setDate("datafim2", datafim2)
                    .list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Avisos selectHoje(Date data) {
        Avisos lista = new Avisos();
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            lista = (Avisos) session.createQuery("from Avisos where dataAviso=:data")
                    .setDate("data", data)
                    .uniqueResult();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
    

    public Avisos buscarPorId(Integer id) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Avisos aviso = (Avisos) session.createQuery("from Avisos where idAviso=:id")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return aviso;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

   

    public boolean insert(Avisos aviso) {
    System.out.println("Entrou no inserte com aviso = "+aviso.getDescriAviso());
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(aviso);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

     

    public boolean delete(Avisos aviso) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(aviso);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Avisos aviso) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(aviso);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean insertFoto(Avisos aviso) {
        try {
            String str1 = aviso.getArquivoAviso();
            String procurada = "base64,";
            int pos = str1.indexOf(procurada) + procurada.length();
            String pc = str1.substring(pos);
            
            String caminho = "C:\\avisos";
            String arquivo = str1;
            BASE64Decoder bd = new BASE64Decoder();
            byte[] buffer = bd.decodeBuffer(pc);
            BufferedImage bufImg = read(new ByteArrayInputStream(buffer));
            //File imgOutFile = new File("Z:"+File.separator +"sigcra" +File.separator+"digitalizacoes"+File.separator+"fotos"+File.separator +arquivo);
            File imgOutFile = new File(caminho, arquivo);

            write(bufImg, "pdf", imgOutFile);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
