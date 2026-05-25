/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author julro
 */
public abstract class Inimigo {
    public Sprite spriteInimigo;
    protected float velocidade;
    public float spawnX;
    public float spawnY;
    protected Animation<TextureRegion> anim;
    protected float tempoAnim;
    protected TextureRegion currentFrame;
    public boolean vivo = true;
    
    public Inimigo(Texture textura, Animation<TextureRegion> anim, float x, float y){
        
        spriteInimigo = new Sprite(textura);
        spriteInimigo.setPosition(x, y);
        this.spawnX = x;
        this.spawnY = y;
        this.anim = anim;
        tempoAnim = 0;
        currentFrame = anim.getKeyFrame(0);
        
    }
    
    public abstract void atualizar(Sprite jogador, float delta);
    
    public void atualizarAnimacao(float delta){
        tempoAnim += delta;
        
        currentFrame = anim.getKeyFrame(tempoAnim, true);
    }
    
    public TextureRegion getFrame(){
        return currentFrame;
    }
    
    public void resetar(){
        spriteInimigo.setPosition(spawnX, spawnY);
        vivo = true;
    }
    
}
