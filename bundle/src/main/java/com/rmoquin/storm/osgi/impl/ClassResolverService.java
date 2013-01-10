package com.rmoquin.storm.osgi.impl;

import backtype.storm.service.IClassResolverService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author rmoquin
 */
public class ClassResolverService implements IClassResolverService {

  @Override
  public Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    return Class.forName(className).newInstance();
  }

/*  @Override
  public byte[] serialize(Object obj) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    try {
      oos.writeObject(obj);
      return bos.toByteArray();
    } finally {
      oos.close();
    }
  }*/

  @Override
  public Object deserialize(byte[] so) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(so));
    try {
      return ois.readObject();
    } finally {
      ois.close();
    }
  }
}
