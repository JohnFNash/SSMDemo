package com.johnfnash.study.util;

import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES �ԳƼ��ܺͽ���
 * 
 * @author Nash
 *
 */
@SuppressWarnings("restriction")
public class SymmetricEncoder {
	private static final String CHARSET = "utf-8";
	private static final String ALGORITHM = "AES";

	/**
	 * ���� 1. ������Կ�������� 2. ���� encodeRules �����ʼ����Կ������ 3. ������Կ 4. �����ͳ�ʼ�������� 5. ���ݼ��� 6.
	 * �����ַ���
	 * 
	 * @param encodeRules
	 * @param content
	 *            ����������
	 * @return
	 */
	public static String AESEncode(String encodeRules, String content) {
		try {
			// 1. ������Կ��������ָ��Ϊ AES �㷨�������ִ�Сд
			KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
			// 2. ���� encodeRules �����ʼ����Կ������
			// ����һ�� 128 λ�����Դ�����ݴ�����ֽ�����
			keygen.init(128, new SecureRandom(encodeRules.getBytes()));
			// 3. ����ԭʼ�Գ���Կ
			SecretKey original_key = keygen.generateKey();
			// 4. ���ԭʼ�Գ���Կ���ֽ�����
			byte[] raw = original_key.getEncoded();
			// 5. �����ֽ��������� AES��Կ
			SecretKey key = new SecretKeySpec(raw, ALGORITHM);
			// 6. ����ָ���㷨 AES �Ƴ�������
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 7.��ʼ������������һ������Ϊ����(Encrypt_mode)���߽��ܽ���(Decrypt_mode)�������ڶ�������Ϊʹ�õ�KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 8.��ȡ�������ݵ��ֽ�����(����Ҫ����Ϊutf-8)��Ȼ��������������ĺ�Ӣ�Ļ�����ľͻ����Ϊ����
			byte[] byte_encode = content.getBytes(CHARSET);
			// 9.�����������ĳ�ʼ����ʽ--���ܣ������ݼ���
			byte[] byte_AES = cipher.doFinal(byte_encode);
			// 10.�����ܺ������ת��Ϊ�ַ���
			// ������Base64Encoder�л��Ҳ�����
			// ����취��
			// ����Ŀ��Build path�����Ƴ�JRE System Library������ӿ�JRE System Library�����±�����һ�������ˡ�
			return new String(new BASE64Encoder().encode(byte_AES));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * ���� ���ܹ��̣� 1.ͬ����1-4�� 2.�����ܺ���ַ������ĳ�byte[]���� 3.���������ݽ���
	 */
	public static String AESDncode(String encodeRules, String content) {
		try {
			// 1.������Կ��������ָ��ΪAES�㷨,�����ִ�Сд
			KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
			// 2.����ecnodeRules�����ʼ����Կ������
			// ����һ��128λ�����Դ,���ݴ�����ֽ�����
			keygen.init(128, new SecureRandom(encodeRules.getBytes()));
			// 3.����ԭʼ�Գ���Կ
			SecretKey original_key = keygen.generateKey();
			// 4.���ԭʼ�Գ���Կ���ֽ�����
			byte[] raw = original_key.getEncoded();
			// 5.�����ֽ���������AES��Կ
			SecretKey key = new SecretKeySpec(raw, ALGORITHM);
			// 6.����ָ���㷨AES�Գ�������
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 7.��ʼ������������һ������Ϊ����(Encrypt_mode)���߽���(Decrypt_mode)�������ڶ�������Ϊʹ�õ�KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 8.�����ܲ����������ݽ�����ֽ�����
			byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			// ����
			byte[] byte_decode = cipher.doFinal(byte_content);
			return new String(byte_decode, CHARSET);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// ����
		System.out.println("ʹ��AES�ԳƼ��ܣ���������ܵĹ���");
		String encodeRules = scanner.next();
		System.out.println("������Ҫ���ܵ�����:");
		String content = scanner.next();
		System.out.println("��������Ĺ���" + encodeRules + "���ܺ��������:" + SymmetricEncoder.AESEncode(encodeRules, content));

		System.out.println("ʹ��AES�Գƽ��ܣ���������ܵĹ���(���������ͬ)");
		encodeRules = scanner.next();
		System.out.println("������Ҫ���ܵ����ݣ����ģ�:");
		content = scanner.next();
		System.out.println("��������Ĺ���" + encodeRules + "���ܺ��������:" + SymmetricEncoder.AESDncode(encodeRules, content));

		scanner.close();
	}

}
