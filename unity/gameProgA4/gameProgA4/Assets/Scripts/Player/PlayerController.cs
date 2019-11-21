using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI; // access ui elements

public class PlayerController : MonoBehaviour
{
    public int jumpForce;
    public int speed;
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
    private float timeLeft;

    public GameObject timeLeftUI;
    public GameObject playerScoreUI;
    private int coinVal;

    // Start is called before the first frame update
    void Start()
    {
        coinVal = 10;
        timeLeft = 120;
        win = false;
        exp = 0;
        toNextLevel = 10;
        level = 1;
        damage = 1;
        rb = GetComponent<Rigidbody2D>();
        col = GetComponent<Collider2D>();
        facingRight = false;
        isGrounded = false;
        jumpForce = 300;
        hasDied = false;
        speed = 10;
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
        timeLeft -= Time.deltaTime;
        Move(); // move the player
        CheckY(); // check to see if you fell off the map
        PlayerRaycast();
        updateGUI();
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

    void updateGUI()
    {
        timeLeftUI.gameObject.GetComponent<Text>().text = "Time: " + (int)timeLeft;
        playerScoreUI.gameObject.GetComponent<Text>().text = "Score: " + score;
    }

    void Move()
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
            moveX * speed,
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
        //Debug.Log("player has collided with " + collision.collider.name + " with tag: " + collision.gameObject.tag);
        //if (collision.gameObject.tag == "groundable") isGrounded = true;
    }
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.name == "Goal") NextLevel();
        if(collision.name == "Coin") EatCoin(collision);
    }

    private void EatCoin(Collider2D collision)
    {
        score += coinVal;
        Destroy(collision.gameObject);
    }

    private void NextLevel()
    {
        CountScore();
        // add exp, manage time
    }

    void CountScore()
    {
        score += (int)timeLeft * 10;
        Debug.Log(score);
    }

    void PlayerRaycast()
    {
        // every time this ray touches an enemy, bounce off his head
        RaycastHit2D hit = Physics2D.Raycast(
            transform.position,
            Vector2.down // shoot a ray down
        );

        if (hit == null || hit.collider == null) return;

        if (hit.distance < 0.9f && hit.collider.tag == "Enemy")
        {
            rb.AddForce(Vector2.up * jumpForce);
        }
        if (hit.distance < 0.9f && hit.collider.tag != "Enemy")
        {
            isGrounded = true;
        }
    }

}
