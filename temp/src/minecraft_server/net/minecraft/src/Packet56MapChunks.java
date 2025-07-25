package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.minecraft.src.Chunk;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet51MapChunk;
import net.minecraft.src.Packet51MapChunkData;

public class Packet56MapChunks extends Packet {

   private int[] field_73589_c;
   private int[] field_73586_d;
   public int[] field_73590_a;
   public int[] field_73588_b;
   private byte[] field_73587_e;
   private byte[][] field_73584_f;
   private int field_73585_g;
   private static byte[] field_73591_h = new byte[0];


   public Packet56MapChunks() {}

   public Packet56MapChunks(List p_i3324_1_) {
      int var2 = p_i3324_1_.size();
      this.field_73589_c = new int[var2];
      this.field_73586_d = new int[var2];
      this.field_73590_a = new int[var2];
      this.field_73588_b = new int[var2];
      this.field_73584_f = new byte[var2][];
      int var3 = 0;

      for(int var4 = 0; var4 < var2; ++var4) {
         Chunk var5 = (Chunk)p_i3324_1_.get(var4);
         Packet51MapChunkData var6 = Packet51MapChunk.func_73594_a(var5, true, '\uffff');
         if(field_73591_h.length < var3 + var6.field_74582_a.length) {
            byte[] var7 = new byte[var3 + var6.field_74582_a.length];
            System.arraycopy(field_73591_h, 0, var7, 0, field_73591_h.length);
            field_73591_h = var7;
         }

         System.arraycopy(var6.field_74582_a, 0, field_73591_h, var3, var6.field_74582_a.length);
         var3 += var6.field_74582_a.length;
         this.field_73589_c[var4] = var5.field_76635_g;
         this.field_73586_d[var4] = var5.field_76647_h;
         this.field_73590_a[var4] = var6.field_74580_b;
         this.field_73588_b[var4] = var6.field_74581_c;
         this.field_73584_f[var4] = var6.field_74582_a;
      }

      Deflater var11 = new Deflater(-1);

      try {
         var11.setInput(field_73591_h, 0, var3);
         var11.finish();
         this.field_73587_e = new byte[var3];
         this.field_73585_g = var11.deflate(this.field_73587_e);
      } finally {
         var11.end();
      }

   }

   public void func_73267_a(DataInputStream p_73267_1_) throws IOException {
      short var2 = p_73267_1_.readShort();
      this.field_73585_g = p_73267_1_.readInt();
      this.field_73589_c = new int[var2];
      this.field_73586_d = new int[var2];
      this.field_73590_a = new int[var2];
      this.field_73588_b = new int[var2];
      this.field_73584_f = new byte[var2][];
      if(field_73591_h.length < this.field_73585_g) {
         field_73591_h = new byte[this.field_73585_g];
      }

      p_73267_1_.readFully(field_73591_h, 0, this.field_73585_g);
      byte[] var3 = new byte[196864 * var2];
      Inflater var4 = new Inflater();
      var4.setInput(field_73591_h, 0, this.field_73585_g);

      try {
         var4.inflate(var3);
      } catch (DataFormatException var11) {
         throw new IOException("Bad compressed data format");
      } finally {
         var4.end();
      }

      int var5 = 0;

      for(int var6 = 0; var6 < var2; ++var6) {
         this.field_73589_c[var6] = p_73267_1_.readInt();
         this.field_73586_d[var6] = p_73267_1_.readInt();
         this.field_73590_a[var6] = p_73267_1_.readShort();
         this.field_73588_b[var6] = p_73267_1_.readShort();
         int var7 = 0;

         int var8;
         for(var8 = 0; var8 < 16; ++var8) {
            var7 += this.field_73590_a[var6] >> var8 & 1;
         }

         var8 = 2048 * 5 * var7 + 256;
         this.field_73584_f[var6] = new byte[var8];
         System.arraycopy(var3, var5, this.field_73584_f[var6], 0, var8);
         var5 += var8;
      }

   }

   public void func_73273_a(DataOutputStream p_73273_1_) throws IOException {
      p_73273_1_.writeShort(this.field_73589_c.length);
      p_73273_1_.writeInt(this.field_73585_g);
      p_73273_1_.write(this.field_73587_e, 0, this.field_73585_g);

      for(int var2 = 0; var2 < this.field_73589_c.length; ++var2) {
         p_73273_1_.writeInt(this.field_73589_c[var2]);
         p_73273_1_.writeInt(this.field_73586_d[var2]);
         p_73273_1_.writeShort((short)(this.field_73590_a[var2] & '\uffff'));
         p_73273_1_.writeShort((short)(this.field_73588_b[var2] & '\uffff'));
      }

   }

   public void func_73279_a(NetHandler p_73279_1_) {
      p_73279_1_.func_72453_a(this);
   }

   public int func_73284_a() {
      return 6 + this.field_73585_g + 12 * this.func_73581_d();
   }

   public int func_73581_d() {
      return this.field_73589_c.length;
   }

}
