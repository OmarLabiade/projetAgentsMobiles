package agent;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class CompressAgent extends AgentImpl {
    private int step = 0;
    private Node targetNode;
    private String fileName;
    private byte[] compressedData;
    private int originalSize;

    public void setInfo(Node node, String file) {
        this.targetNode = node;
        this.fileName = file;
    }

    @Override
    public void main() {
        if (step == 0) {
            System.out.println("Depart vers le serveur pour compresser : " + fileName);
            step = 1;
            move(targetNode);
        }
        else if (step == 1) {
            byte[] rawData = (byte[]) getNameServer().get(fileName);

            if (rawData == null) {
                System.out.println("ERREUR : Fichier '" + fileName + "' introuvable sur ce serveur !");
                step = 2;
                back();
                return;
            }

            originalSize = rawData.length;
            System.out.println("Fichier trouve avec " + originalSize + " octets, compression encore");

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                GZIPOutputStream gzip = new GZIPOutputStream(bos);
                gzip.write(rawData);
                gzip.close();
                this.compressedData = bos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }

            step = 2;
            back();
        }
        else if (step == 2) {
            if (compressedData != null) {
                long endTime = System.currentTimeMillis();
                long duration = endTime - RunCompressClient.startTime;
                System.out.println("Temps Total Agent : " + duration + " ms");
                System.out.println("Taille originale : " + originalSize);
                System.out.println("Taille compressee: " + compressedData.length);
                double gain = 100.0 * (originalSize - compressedData.length) / originalSize;
                System.out.printf("Gain Bande Passante : %.2f %%\n", gain);
            } else {
                System.out.println("Echec : Retour a vide.");
            }
        }
    }
}