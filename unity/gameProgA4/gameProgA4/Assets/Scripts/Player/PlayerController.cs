using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerController : MonoBehaviour
{
    public int jumpForce;
    public int playerSpeed;
    public int yDead;

    public int health, maxHealth;
    public int score, exp, level, toNextLevel;
    public int damage;
    public bool win;

    private bool facingRight;
    public bool hasDied;
    public bool isGrounded;

    private float moveX, moveY;
    private float distGround;
    private Rigidbody2D rb;
    private Collider2D col;
    private Vector2 size;

    // Start is called before the first frame update
    void Start()
    {
        win = false;
        exp = 0;
        toNextLevel = 10;
        level = 1;
        damage = 1;
        rb = GetComponent<Rigidbody2D>();
        col = GetComponent<Collider2D>();
        facingRight = false;
        isGrounded = false;
        jumpForce = 500;
        hasDied = false;
        playerSpeed = 10;
        yDead = -10;
        maxHealth = 4;
        health = maxHealth;
        score = 0;
        size = col.bounds.size;
        distGround = col.bounds.extents.y;
        //print(size.ToString() + col.ToString());
    }

    // Update is called once per frame
    void FixedUpdate()
    {
        PlayerMove(); // move the player
        CheckY(); // check to see if you fell off the map
    }

    private void CheckY()
    {
        // this function will check the y value of the player, as well as the y velocity
        // this is actual dogshit garbage and you should feel bad for using this
        //if ((Vector3)rb.velocity == Vector3.zero) isGrounded = true; 
        //else isGrounded = false;
        if (transform.position.y <= yDead && !hasDied)
        {
            Debug.Log("you fell to death!");
            RestartGame();
        }
    }

    void LoadMenu()
    {
        SceneManager.LoadScene("MainMenu");
    }

    void ResetPlayer()
    {
        health = maxHealth;
        hasDied = false;
        score /= 2;
    }

    void RestartGame()
    {
        Die();
        ResetPlayer();
        LoadMenu();
    }

    private IEnumerator Die()
    {
        hasDied = true;
        yield return new WaitForSeconds(1.5f);
    }

    void PlayerMove()
    {
        // controls
        moveX = Input.GetAxis("Horizontal");
        if (Input.GetButtonDown("Jump") && isGrounded) Jump();
        // animations
        // player direction
        if (moveX < 0.0f && !facingRight) FlipPlayer();
        else if (moveX > 0.0f && facingRight) FlipPlayer();
        // physics
        rb.velocity = new Vector2(
            moveX * playerSpeed,
            rb.velocity.y
        );
    }

    void FlipPlayer()
    {
        facingRight = !facingRight;
        Vector2 localScale = transform.localScale;
        localScale.x *= -1;
        transform.localScale = localScale;
    }

    void Jump()
    {
        rb.AddForce(Vector2.up * jumpForce);
        isGrounded = false;
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        // so using tilemaps, we can make new tilemaps and assign different tags to them, for ex: water and ground.
        Debug.Log("player has collided with " + collision.collider.name + " with tag: " + collision.gameObject.tag);
        if (collision.gameObject.tag == "groundable") isGrounded = true;
    }
}
