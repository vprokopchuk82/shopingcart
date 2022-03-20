package com.vprokopchuk.shoppingcart.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class IdGenerator implements Runnable
{
    protected SecureRandom ran = null;
    protected int queueSize = 1000;
    protected int minSize = 200;
    protected long queueWatchTime = 1000;
    protected volatile AtomicBoolean stop = new AtomicBoolean(false);
    protected PriorityBlockingQueue<_strong_id_material> queue = new PriorityBlockingQueue<_strong_id_material>(queueSize);
    protected ExecutorService executor = Executors.newSingleThreadExecutor();

    {
        try {
            init();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * _strong_id_material is a data structure used to encapsulate ID gen.
     * @author michaelmedvinksy
     */
    protected class _strong_id_material implements Comparable<_strong_id_material>
    {
        protected long baseMaterial;
        //offset
        protected int extMaterial;
        protected String alphas;

        /**
         * @return the alphas
         */
        public String getAlphas() {
            return alphas;
        }
        /**
         * @param alphas the alphas to set
         */
        public void setAlphas(String alphas) {
            this.alphas = alphas;
        }
        /**
         * @return the baseMaterial
         */
        public long getBaseMaterial()
        {
            return baseMaterial;
        }
        /**
         * @param baseMaterial the baseMaterial to set
         */
        public void setBaseMaterial(long baseMaterial)
        {
            this.baseMaterial = baseMaterial;
        }
        /**
         * @return the extMaterial
         */
        public int getExtMaterial()
        {
            return extMaterial;
        }
        /**
         * @param extMaterial the extMaterial to set
         */
        public void setExtMaterial(int extMaterial)
        {
            this.extMaterial = extMaterial;
        }
        public int compareTo(_strong_id_material o)
        {
            Long l = this.baseMaterial;
            return l.compareTo(this.baseMaterial);
        }

    }

    /**
     * @return the queueSize
     */
    public int getQueueSize()
    {
        return queueSize;
    }

    /**
     * @param queueSize the queueSize to set
     */
    public void setQueueSize(int queueSize)
    {
        this.queueSize = queueSize;
    }

    /**
     * @return the minSize
     */
    public int getMinSize()
    {
        return minSize;
    }

    /**
     * @param minSize the minSize to set
     */
    public void setMinSize(int minSize)
    {
        this.minSize = minSize;
    }

    /**
     * Make a new material for ids.
     * @return
     */
    protected _strong_id_material makeMaterial()
    {
        _strong_id_material m = new _strong_id_material();
        m.setBaseMaterial(Math.abs(this.ran.nextLong()));
        m.setExtMaterial(Math.abs(this.ran.nextInt()));
        return m;
    }

    /**
     * @throws NoSuchAlgorithmException
     */
    public void init() throws NoSuchAlgorithmException
    {
        this.ran = SecureRandom.getInstance("SHA1PRNG");
        ran.setSeed(ran.generateSeed(16));
        for (int i = 0; i < this.queueSize; i++)
        {
            this.queue.offer(makeMaterial());
        }
        executor.execute(this);
    }

    /**
     * Generate standard java UUID()
     * @see UUID#randomUUID()
     */
    public UUID getUUID()
    {
        return UUID.randomUUID();
    }


    public String generateId(int strength)
    {
        _strong_id_material m = this.queue.poll();
        if (m == null)
        {
            m = makeMaterial();
        }
        StringBuffer buf = new StringBuffer();
        if (strength == 1)
        {
            buf.append(m.getBaseMaterial());
        }
        else if (strength == 2)
        {
            buf.append(m.getBaseMaterial()).append(m.getExtMaterial());
        }
        else if (strength > 2)
        {
            buf.append(System.nanoTime()).append(m.getBaseMaterial()).append(m.getExtMaterial());
        }
        return buf.toString();
    }

    public void run()
    {
        while (!stop.get())
        {
            try {Thread.sleep(queueWatchTime);} catch (InterruptedException e) {}
            if (stop.get())
            {
                return;
            }
            if (this.queue.size() <= minSize)
            {
                long x = this.queue.size();
                for (int i = 0; i < (this.queueSize - x); i++)
                {
                    if (stop.get())
                    {
                        return;
                    }
                    this.queue.offer(makeMaterial());
                }
            }
        }
    }

    public void shutdown()
    {
        this.stop.set(true);
        this.executor.shutdownNow();
    }
}